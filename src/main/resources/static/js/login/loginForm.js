// 쿠키를 설정하는 함수
function setCookie(cname, cvalue, exdays) {
    let d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    let expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

// 쿠키를 가져오는 함수
function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

// 아이디 기억하기 체크박스를 클릭할 때 실행되는 함수
function rememberId() {
    let loginId = document.getElementById("loginId").value;
    // 체크박스가 체크되어 있으면, 쿠키에 아이디를 저장합니다.
    if (document.getElementById("flexCheckDefault").checked) {
        setCookie("rememberedId", loginId, 30); // 쿠키는 30일 동안 유효합니다.
    } else {
        // 체크박스가 체크되어 있지 않으면, 쿠키를 삭제합니다.
        document.cookie = "rememberedId=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    }
}

function togglePasswordVisibility() {
    let passwordInput = document.getElementById("password");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
    } else {
        passwordInput.type = "password";
    }
}
document.addEventListener('DOMContentLoaded', function () {

    // 쿠키에서 아이디를 가져옵니다.
    let rememberedId = getCookie("rememberedId");
    // 아이디가 존재하면, 아이디 입력 필드에 자동으로 값을 설정합니다.
    if (rememberedId !== "") {
        document.getElementById("loginId").value = rememberedId;
    }

    document.getElementById('loginBtn').addEventListener('click', function(event) {
        event.preventDefault();
        rememberId();
        document.getElementById('loginForm').submit();
    });

    document.getElementById('passwordShow').addEventListener('click', function () { togglePasswordVisibility();})
    document.getElementById('flexCheckDefault').addEventListener('change', function() { rememberId(); });
});