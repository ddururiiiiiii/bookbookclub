function previewImage(event) {
    const input = event.target;

    // 파일이 선택되지 않았거나 선택된 파일이 이미지가 아닌 경우 미리보기를 표시하지 않음
    if (!input || !input.files || input.files.length === 0 || !input.files[0].type.startsWith('image/')) {
        return;
    }

    // FileReader 객체를 사용하여 이미지 파일을 읽어오고 미리보기를 업데이트함
    const reader = new FileReader();
    reader.onload = function () {
        const preview = document.getElementById('imagePreview');
        preview.src = reader.result;
        preview.style.display = 'block'; // 이미지 미리보기 표시
    }
    reader.readAsDataURL(input.files[0]); // 파일을 base64 문자열로 읽어오기
    document.getElementById("imagePreviewEmpty").style.display = 'none';
}

function idValidate(){
    let idInput = document.getElementById('id');
    let idError = document.getElementById('idError');

    if (idInput.value.trim() === '') {
        idInput.classList.add('is-invalid');
        idError.style.display = '';
        idError.textContent = 'ID는 필수입니다.';
    } else {
        idInput.classList.remove('is-invalid');
        idError.style.display = 'none';
    }

    let idRegExp = /^[a-zA-Z0-9!_.-]{4,12}$/;
    if( !idRegExp.test(idInput.value) ) {
        idInput.classList.add('is-invalid');
        idError.style.display = '';
        idError.textContent = '4~12글자, 영어나 숫자만 가능합니다. (입력불가특수문자 : @\#$%&=/)';
    } else {
        idInput.classList.remove('is-invalid');
        idError.style.display = 'none';
    }
}

function nickNameValidate(){
    let nickNameInput = document.getElementById('nickName');
    let nickNameError = document.getElementById('nickNameError');

    if (nickNameInput.value.trim() === '') {
        nickNameInput.classList.add('is-invalid');
        nickNameError.style.display = '';
        nickNameError.textContent = '닉네임은 필수입니다.';
    } else {
        nickNameInput.classList.remove('is-invalid');
        nickNameError.textContent = 'none';
    }

    let nicknameRegExp = /^[a-zA-Z0-9가-힣]{1,20}$/;
    if( !nicknameRegExp.test(nickNameInput.value) ) {
        nickNameInput.classList.add('is-invalid');
        nickNameError.style.display = '';
        nickNameError.textContent = '1~20글자, 한글, 영어, 숫자만 가능합니다.';
    } else {
        nickNameInput.classList.remove('is-invalid');
        nickNameError.style.display = 'none';
    }
}

function passwordValidate() {
    let passwordInput = document.getElementById('password');
    let passwordError = document.getElementById('passwordError');
    if (passwordInput.value.trim() === '') {
        passwordInput.classList.add('is-invalid');
        passwordError.textContent = '비밀번호는 필수 입니다.';
    } else {
        passwordInput.classList.remove('is-invalid');
        passwordError.textContent = '';
    }

    let passwordRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>]).{8,}$/;
    if (!passwordRegExp.test(passwordInput.value)) {
        passwordInput.classList.add('is-invalid');
        passwordError.style.display = '';
        passwordError.textContent = '최소 8자 이상 영문, 한글, 숫자, 특수문자가 포함되어야 합니다.';
    } else {
        passwordInput.classList.remove('is-invalid');
        passwordError.style.display = 'none';
    }
}

function checkPasswordValidate(){
    let passwordInput = document.getElementById('password');
    let checkPasswordInput = document.getElementById('checkPassword');

    let checkPasswordSuccess = document.getElementById('checkPasswordSuccess');
    let checkPasswordError = document.getElementById('checkPasswordError');

    if ( passwordInput.value.trim() !== '' ) {
        if (passwordInput.value === checkPasswordInput.value) {
            checkPasswordSuccess.style.display = '';
            checkPasswordError.style.display = 'none';
            checkPasswordInput.classList.remove('is-invalid');
            checkPasswordInput.classList.add('is-valid');
            checkPasswordSuccess.textContent = '비밀번호가 일치합니다.';
        } else {
            checkPasswordSuccess.style.display = 'none';
            checkPasswordError.style.display = '';
            checkPasswordInput.classList.add('is-invalid');
            checkPasswordInput.classList.remove('is-valid');
            checkPasswordError.textContent = '비밀번호가 일치하지 않습니다.';
        }
    }
}

function biosValidate(){
    let bioInput = document.getElementById('bios');
    let maxLength = 120;
    let textLength = bioInput.value.length;
    let biosError = document.getElementById("biosError");
    let counter = document.getElementById('biosCounter');

    if (textLength > maxLength) {
        bioInput.classList.add('is-invalid');
        bioInput.value = bioInput.value.substring(0, maxLength);
        biosError.style.display = 'block';
        biosError.textContent = '120자까지만 입력이 가능합니다.';
        counter.style.color = 'red';
    } else {
        bioInput.classList.remove('is-invalid');
        biosError.style.display = 'none';
        biosError.textContent = '';
        counter.style.color = 'gray';
    }
    counter.textContent = textLength + '/' + maxLength;
}

document.addEventListener('DOMContentLoaded', function () {

    document.getElementById('saveBtn').addEventListener('click', function(event) {
        event.preventDefault();
        if (!document.getElementById('id').classList.contains('is-invalid')
            && !document.getElementById('nickName').classList.contains('is-invalid')
            && !document.getElementById('password').classList.contains('is-invalid')
            && !document.getElementById('checkPassword').classList.contains('is-invalid')) {
            document.getElementById('joinForm').submit();
        }
    });

    document.getElementById('id').addEventListener('keyup', function() { idValidate(); });
    document.getElementById('nickName').addEventListener('keyup', function() { nickNameValidate(); });
    document.getElementById('password').addEventListener('keyup', function() { passwordValidate(); });
    document.getElementById('checkPassword').addEventListener('keyup', function() { checkPasswordValidate(); });
    document.getElementById('bios').addEventListener('keyup', function() { biosValidate(); });
    document.getElementById('profileImage').addEventListener('change', previewImage);


});
