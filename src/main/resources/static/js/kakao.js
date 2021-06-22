Kakao.init('b9de844ef665dda98917b7ec7b9d9a8b');

isLogin();

function kakaoLogin() {
    let value = localStorage.getItem('token');

    if (value === null) {
        Kakao.Auth.login({
            success: (authObj) => {
                fetchToken(authObj['access_token']);
                document.getElementById('login-btn').innerText = '로그아웃';
            },
            fail: (err) => {
                alert(JSON.stringify(err));
            }
        });
    } else if (value !== null) {
        Kakao.Auth.logout(() => {
            console.log(Kakao.Auth.getAccessToken());
        });
        localStorage.removeItem('token');
        document.getElementById('login-btn').innerText = '로그인';
    }
}

function isLogin() {
    let value = localStorage.getItem('token');
    if (value === null) {
        document.getElementById('login-btn').innerText = '로그인';
    } else {
        document.getElementById('login-btn').innerText = '로그아웃';
    }
}

function fetchToken(token) {
    let url = '/auth/kakao/callback';
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            accessToken: token
        })
    }).then((response) => response.json()
    ).then((json) => {
        localStorage.setItem("token", json.data.token);
        redirect();
    });
}

function redirect() {
    fetch('/admin')
        .then();
}

function goSwagger() {
    location.href = '/swagger-ui.html';
}