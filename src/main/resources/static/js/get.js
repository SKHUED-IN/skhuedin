function userDelete(id) {
    var sendData = "id=" + id;
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    if (confirm("삭제하시겠습니까?")) {
        $.ajax({
            url: 'userDelete'
            , method: 'POST'
            , data: sendData

            , success: function (resp) {
                return userList();
            }
        })
    }
}

function userRoleChange(id) {
    var sendData = "id=" + id;
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    if (confirm("변경하시겠습니까?")) {
        $.ajax({
            url: 'user/role'
            , method: 'POST'
            , data: sendData
            , success: function (resp) {
                return userList();
            }
        })
    }
}

function questionStatusChange(id) {

    var sendData = "id=" + id;
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    if (confirm("변경하시겠습니까?")) {
        $.ajax({
            url: 'question/status'
            , method: 'POST'
            , data: sendData
            , success: function (resp) {
                return questionList();
            }
        })
    }
}


function kakao(token) {
    var sendData = {accessToken: token}
    var jsonData = JSON.stringify(sendData);
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기

    $.ajax({
        url: 'auth/kakao/callback'
        , contentType: 'application/json; charset=UTF-8'
        , method: 'POST'
        , data: jsonData
        , dataType: 'json'
        , success: function (resp) {
            localStorage.setItem("token", resp.token);
        }
    })
}

function userList() {
    var xhr = http();
    var token = localStorage.getItem("token");
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url: 'userList'
        , method: 'POST'
        , data: JSON.stringify()
        , contentType: 'application/json; charset=UTF-8'
        , dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        }
        , success: function output(resp) {
            var result = '';
            result += '<table border="1" style="margin-left: auto; margin-right: auto; width: 1300px;">'
            result += '<tr><th> ID </th><th>이메일</th><th>이름</th><th>역할</th><th>삭제</th></tr>'
            $.each(resp, function (index, item) {
                result += '<tr><td>' + item["id"] + '</td>'
                result += '<td>' + item["email"] + '</td>'
                result += '<td  style ="width : 200px;">' + item["name"] + '</td>'
                result += '<td style ="width : 250px;">' + item["role"] + '/ <button type="button" onclick=userRoleChange(' + item["id"] + ')>변경</button></td>'
                result += '<td><button type="button" onclick=userDelete(' + item["id"] + ')>삭제</button></td></tr>'
            })
            result += '</table>'
            $('#result').html(result)
        }
    })
    $('#result').html(defaultText())
}


function questionList() {
    var xhr = http();
    const token = localStorage.getItem("token");
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url: 'questionList'
        , method: 'POST'
        , data: JSON.stringify()
        , contentType: 'application/json; charset=UTF-8'
        , dataType: 'json'
        , beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        }
        , success: function output(resp) {
            var result = '';
            result += '<table border="1" style="margin-left: auto; margin-right: auto; width: 1300px;>'
            result += '<tr style="background: #1d84df; color: #ffffff;"><th>제목</th><th>조회수</th><th>비공개여부</th><th>디테일</th></tr>'
            $.each(resp, function (index, item) {
                result += '<tr><td style ="width : 300px;">' + item["title"] + '</td>'
                result += '<td style ="width : 300px;">' + item["view"] + '</td>'
                result += '<td style ="width : 250px;">' + item["status"] + '/ <button type="button" onclick=questionStatusChange(' + item["id"] + ')>변경</button></td>'
                result += '<td style ="width : 300px;"><button type="button" onclick=commentList(' + item["id"] + ')>댓글 보기</button></td></tr>'
            })
            result += '</table>'
            $('#result').html(result)
        }
    })
    $('#result').html(defaultText())
}

function commentList(id) {
    var sendData = "id=" + id;
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url: 'questionDetail'
        , method: 'POST'
        , data: sendData
        , success: function (resp) {
            var result = '';
            result += '<table border="1" style="margin-left: auto; margin-right: auto; width: 1300px;>'
            result += '<tr style="background: #1d84df; color: #ffffff;"><th style ="width :220px;"> ID </th><th>parentId</th><th>content</th><th><button type="button" onclick=questionList()>뒤로가기</button></th></tr>'
            $.each(resp, function (index, item) {
                result += '<tr><td style ="width : 300px;">' + item["id"] + '</td>'
                result += '<td style ="width : 300px;">' + item["parentId"] + '</td>'
                result += '<td style ="width : 300px;">' + item["content"] + '</td></tr>'
            })
            result += '</table>'
            $('#result').html(result)
        }
    })
}

function postList() {
    var xhr = http();
    var token = localStorage.getItem("token");
    $.ajax({
        url: 'postList'
        , method: 'POST'
        , data: JSON.stringify()
        , contentType: 'application/json; charset=UTF-8'
        , dataType: 'json'
        , beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        }
        , success: function output(resp) {
            var result = '';
            result += '<table border="1" style="margin-left: auto; margin-right: auto; width: 1300px;">'
            result += '<tr><th>글쓴이</th><th>제목</th><th>조회수</th><th>설정</th><th>상태 / 삭제</th></tr>'
            $.each(resp, function (index, item) {
                result += '<tr><td style ="width : 100px;">' + item["name"] + '</td>'
                result += '<td style ="width : 300px;">' + item["title"] + '</td>'
                result += '<td style ="width : 150px;">' + item["view"] + '</td>'
                result += '<td style ="width : 300px;"><form action="update/category" method="post">' +
                    '<select name="category">' +
                    '<option value="" selected="">카테고리선택</option>' +
                    innerCategoryList(item["category"]) +
                    '</select>' +
                    '<select name="post_id">' +
                    '<option value="' + item["id"] + '"></option>' +
                    '</select>' +
                    '<input type="submit" value ="변경"></form>' +
                    '</td>'
                result += '<td style ="width : 300px;">' + item["deleteStatus"] + ' <button type="button" onclick=postDelete(' + item["id"] + ')>삭제</button></td></tr>'
            })
            result += '</table>'
            $('#result').html(result)
        }
    })
    $('#result').html(defaultText())
}

function innerCategoryList(id) {
    var xhr = http();
    var token = localStorage.getItem("token");
    var result = '';
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url: 'categoryList'
        , method: 'POST'
        , async: false // 동기 방식을 위한 설정
        , data: JSON.stringify()
        , contentType: 'application/json; charset=UTF-8'
        , dataType: 'json'
        , beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        }
        , success: function output(resp) {
            var count = 1;
            $.each(resp, function (index, item) {
                if (count == id){
                    result += '<option value= "' + (count) + '" selected>' + item["name"] + '</option>'
                    count += 1;
                }else {
                    result += '<option value= "' + (count) + '">' + item["name"] + '</option>'
                    count += 1;
                }
            })
        }
    });
    return result;
}

function categoryList() {
    var xhr = http();
    var token = localStorage.getItem("token");
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url: 'categoryList'
        , method: 'POST'
        , data: JSON.stringify()
        , contentType: 'application/json; charset=UTF-8'
        , dataType: 'json'
        , beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        }
        , success: function output(resp) {
            var result = '';
            result += '<table border="1" style="margin-left: auto; margin-right: auto; width: 1300px;>'
            result += '<tr style="background: #1d84df; color: #ffffff;"><th style ="width :180px;"> ID </th><th>name</th><th>글 수 </th><th>가중치 / 변경</th><th><a href="/create/category"><button type="button">생성</button></a></th></tr>'
            $.each(resp, function (index, item) {
                result += '<tr><td style ="width : 250px;">' + item["id"] + '</td>'
                result += '<td style ="width : 250px;">' + item["name"] + '</td>'
                result += '<td style ="width : 250px;">' + item["referPostCount"] + '</td>'
                result += '<td style ="width : 250px;">' + item["weight"] + ' / ' + '<button type="button" onclick=categoryUp(' + item["id"] + ')>↑</button><button type="button" onclick=categoryDown(' + item["id"] + ')>↓</button></td>'
                result += '<td style ="width : 250px;"><button type="button" onclick=categoryDelete(' + item["id"] + ',' + item["referPostCount"] + ')>삭제</button></td></tr>'
            })
            result += '</table>'
            return $('#result').html(result)
        }
    })
   return $('#result').html(defaultText())
}

function postDelete(id) {

    var sendData = "id=" + id;
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기

    if (confirm("삭제하시겠습니까?")) {
        $.ajax({
            url: 'postDelete'
            , method: 'POST'
            , data: sendData
            , success: function (resp) {
                return postList();
            }
        })
    }
}


function categoryDelete(id, referPostCount) {

    var sendData = "id=" + id;
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기

    if (referPostCount == 0) {
        if (confirm("삭제하시겠습니까?")) {
            $.ajax({
                url: 'categoryDelete'
                , method: 'POST'
                , data: sendData
                , success: function (resp) {
                    return categoryList();
                }
            })
        }
    } else {
        alert("현재 참조하고 있는 글이 " + referPostCount + "개 가 있기 때문에 삭제하지 못합니다.");
        return categoryList();
    }
}

function categoryUp(id) {

    var sendData = "id=" + id;
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url: 'categoryList/up'
        , method: 'POST'
        , data: sendData
        , success: function (resp) {
            return categoryList();
        }
    })
}

function categoryDown(id) {

    var sendData = "id=" + id;
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url: 'categoryList/down'
        , method: 'POST'
        , data: sendData
        , success: function (resp) {
            return categoryList();
        }
    })
}

function http() {

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
            document.getElementById("text").innerHTML = xhr.responseText;
        }
    };
    return xhr;
}

function defaultText() {
    var result = '<br><br><h1 style ="color : black; font-family: \'BMEULJIRO\'";> 관리자가 아니신 분들은 접근 불가입니다람쥐</h1>';
    return result;
}

function kakao2(){
    var a ="";

    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url: 'kakao'
        , method: 'GET'
        , async: false // 동기 방식을 위한 설정
        , success: function (resp) {
            a = resp["kakaoId"]

        }
    })
    return a;
}


