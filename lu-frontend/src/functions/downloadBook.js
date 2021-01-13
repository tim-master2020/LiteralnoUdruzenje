import { defaultUrl } from '../backendConfig.js';
import streamSaver from 'streamsaver';

export function downloadBook(e, book) {
    e.preventDefault();
    var url = `${defaultUrl}/api/books/download/${book}`;
    fetch(url, {
        method: 'GET',
    })
    .then(response => {  
    
        const fileStream = streamSaver.createWriteStream(book.concat('.pdf'));   
        const readableStream = response.body;

        // More optimized
        if (readableStream.pipeTo) {
            return readableStream.pipeTo(fileStream);
        }   
    
        const writer = fileStream.getWriter();
    
        const reader = response.body.getReader();
        const pump = () => reader.read()
            .then(res => res.done
                ? writer.close()
                : writer.write(res.value).then(pump));
    
        pump();
    })
    .catch(error => {
        console.log(error);
    });
}