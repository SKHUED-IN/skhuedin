


fetch("/login", {
    method: 'post',
    headers: {
        'content-type': 'application/json'
    },
    body: JSON.stringify({
        name: $name.value,
        pwd: $pwd.value
    })
}).then(res => res.json())
    .then(token => {
        console.log(token)
        localStorage.setItem("jwt", token.accessToken)
        alert("로그인 되었습니다");
    });

fetch("/info", {
    method: 'get',
    headers: {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem("jwt"),
    }
}).then(res => res.json())
    .then(json => alert("이름 : " + json.name + ", 비밀번호 : " + json.pwd));