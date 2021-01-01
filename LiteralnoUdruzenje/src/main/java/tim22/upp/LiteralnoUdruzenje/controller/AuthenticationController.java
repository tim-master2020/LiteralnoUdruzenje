package tim22.upp.LiteralnoUdruzenje.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tim22.upp.LiteralnoUdruzenje.dto.ReaderDTO;
import tim22.upp.LiteralnoUdruzenje.dto.TaskDTO;
import tim22.upp.LiteralnoUdruzenje.dto.WriterDTO;
import tim22.upp.LiteralnoUdruzenje.model.*;
import tim22.upp.LiteralnoUdruzenje.security.TokenUtils;
import tim22.upp.LiteralnoUdruzenje.security.auth.JwtAuthenticationRequest;
import tim22.upp.LiteralnoUdruzenje.service.IReaderService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;
import tim22.upp.LiteralnoUdruzenje.service.impl.CustomUserDetailsService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private IReaderService readerService;

    @Autowired
    private IWriterService writerService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest) {

        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
        if(authentication == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails details = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String username = authenticationRequest.getUsername();

        if(userService.findByUsername(username).getRole().equals(Role.READER) && userService.findByUsername(username).isActiveAccount()){
            String jwt = tokenUtils.generateToken(details.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        }


        if (userService.findByUsername(username).getRole().equals(Role.WRITER)) {
            Writer writer = writerService.findByUsername(username);
            if(writer.isVerified()) {
                String jwt = tokenUtils.generateToken(details.getUsername());
                int expiresIn = tokenUtils.getExpiredIn();

                return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ResponseEntity<?> getCurrentUser() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) a.getPrincipal();

        if(user.getRole().equals(Role.READER)) {
            Reader reader = readerService.findByEmail(user.getEmail());
            ReaderDTO readerDTO = modelMapper.map(reader, ReaderDTO.class);
            return new ResponseEntity<>(readerDTO, HttpStatus.OK);

        } else if ( user.getRole().equals(Role.WRITER)){
            Writer writer = writerService.findByEmail(user.getEmail());
            WriterDTO writerDTO = modelMapper.map(writer, WriterDTO.class);
            List<Task> tasks = taskService.createTaskQuery().taskAssignee(user.getUsername()).list();
            writerDTO.setTasks(mapTasks(tasks));
            return new ResponseEntity<>(writerDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private List<TaskDTO> mapTasks(List<Task> tasks){
        List<TaskDTO> taskDTOs = new ArrayList<>();
        for(Task task : tasks){
            TaskDTO taskDTO = new TaskDTO(task.getId(), task.getProcessInstanceId(), task.getName(), task.getAssignee());
            taskDTOs.add(taskDTO);
        }
        return taskDTOs;
    }
}
