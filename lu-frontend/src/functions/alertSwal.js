import withReactContent from 'sweetalert2-react-content';
import Swal from 'sweetalert2';

const alertPopup = withReactContent(Swal)

export function alert(msg) {
    return alertPopup.fire({
        text: msg,
    });
}