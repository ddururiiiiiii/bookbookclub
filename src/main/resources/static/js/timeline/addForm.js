

function contentValidate(){
    let contentInput = document.getElementById('content');
    let maxLength = 120;
    let textLength = contentInput.value.length;
    let contentError = document.getElementById("contentError");
    let counter = document.getElementById('contentCounter');

    if (textLength > maxLength) {
        contentInput.classList.add('is-invalid');
        contentInput.value = contentInput.value.substring(0, maxLength);
        contentError.style.display = 'red';
        contentError.textContent = '120자까지만 입력이 가능합니다.';
        counter.style.color = 'red';
    } else {
        contentInput.classList.remove('is-invalid');
        contentError.style.display = 'none';
        contentError.textContent = '';
        counter.style.color = 'gray';
    }

    counter.textContent = textLength + '/' + maxLength;
}

document.addEventListener('DOMContentLoaded', function () {

    document.getElementById('saveBtn').addEventListener('click', function(event) {
        event.preventDefault();
        if (!document.getElementById('content').classList.contains('is-invalid')) {
            document.getElementById('addForm').submit();
        }
    });

    document.getElementById('content').addEventListener('keyup', function() { contentValidate(); });

});
