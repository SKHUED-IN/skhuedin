function userList() {
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url:'userList'
        , method : 'POST'
        , data: JSON.stringify()
        ,contentType : 'application/json; charset=UTF-8'
        ,dataType : 'json'
        , success :function output(resp){
                       var result = '';
                       result +='<table border="1" style="margin-left: auto; margin-right: auto; width: 1300px;>'
                       result +='<tr style="background: #1d84df; color: #ffffff;><th style ="width :220px;"> ID </th><th>입학 년도</th><th>졸업 년도</th><th>이메일</th><th>이름</th><th>삭제</th></tr>'
                       $.each(resp,function(index,item){
                          result +='<tr><td style ="width : 300px;">'+item["id"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["entranceYear"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["graduationYear"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["email"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["name"]+'</td>'
                          result +='<td style ="width : 300px;"><button type="button" onclick=userDelete('+item["id"]+')>삭제</button></td></tr>'
                       })
                       result +='</table>'
                       $('#result').html(result)
                   }
    })
}

function postList() {
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url:'postList'
        , method : 'POST'
        , data: JSON.stringify()
        ,contentType : 'application/json; charset=UTF-8'
        ,dataType : 'json'
        , success :function output(resp){
                       var result = '';
                       result +='<table border="1" style="margin-left: auto; margin-right: auto; width: 1300px;>'
                       result +='<tr style="background: #1d84df; color: #ffffff;><th style ="width :220px;"> ID </th><th>글쓴이</th><th>제목</th><th>내용</th><th>조회수</th><th>카테고리</th><th>설정</th><th>삭제</th></tr>'
                       $.each(resp,function(index,item){
                          result +='<tr><td style ="width : 300px;">'+item["id"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["name"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["title"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["content"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["view"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["category"]+'</td>'
                          result +='<td style ="width : 300px;"><form action="update/category" method="post">'+
                                                                                       '<select name="category">'+
                                                                                       '<option value="">카테고리선택</option>'+
                                                                                       '<option value="1">학창시절</option>'+
                                                                                       '<option value="2">취업준비</option>'+
                                                                                       '<option value="3">하고싶은말</option>'+
                                                                                       '</select>'+
                                                                                       '<select name="post_id">'+
                                                                                       '<option value="'+item["id"]+'"></option>'+
                                                                                       '</select>'+
                                                                                       '<input type="submit" value ="카테고리 변경"></form>'+
                                                                                       '</td>'
                          result +='<td style ="width : 300px;"><button type="button" onclick=postDelete('+item["id"]+')>삭제</button></td></tr>'
                       })
                       result +='</table>'
                       $('#result').html(result)
                   }
    })
}


function questionList() {
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url:'questionList'
        , method : 'POST'
        , data: JSON.stringify()
        ,contentType : 'application/json; charset=UTF-8'
        ,dataType : 'json'
        , success :function output(resp){
                       var result = '';
                       result +='<table border="1" style="margin-left: auto; margin-right: auto; width: 1300px;>'
                       result +='<tr style="background: #1d84df; color: #ffffff;><th style ="width :220px;"> ID </th><th>title</th><th>content</th></tr>'
                       $.each(resp,function(index,item){
                          result +='<tr><td style ="width : 300px;">'+item["id"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["title"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["content"]+'</td></tr>'
                       })
                       result +='</table>'
                       $('#result').html(result)
                   }
    })
}

function categoryList() {
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url:'categoryList'
        , method : 'POST'
        , data: JSON.stringify()
        ,contentType : 'application/json; charset=UTF-8'
        ,dataType : 'json'
        , success :function output(resp){
                       var result = '';
                       result +='<table border="1" style="margin-left: auto; margin-right: auto; width: 1300px;>'
                       result +='<tr style="background: #1d84df; color: #ffffff;><th style ="width :220px;"> ID </th><th>name</th><th>weight</th><th>weight 변경</th></tr>'
                       $.each(resp,function(index,item){
                          result +='<tr><td style ="width : 300px;">'+item["id"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["name"]+'</td>'
                          result +='<td style ="width : 300px;">'+item["weight"]+'</td>'
                          result +='<td style ="width : 300px;"><button type="button" onclick=categoryUp('+item["id"]+')>↑</button><button type="button" onclick=categoryDown('+item["id"]+')>↓</button></td></tr>'
                       })
                       result +='</table>'
                       $('#result').html(result)
                   }
    })
}
function userDelete(id) {
    var sendData = "id="+id;
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url:'userDelete'
        , method : 'POST'
        , data: sendData
        , success :function(resp){
         return userList();
         }
       })
}


function postDelete(id) {
    var sendData = "id="+id;
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url:'postDelete'
        , method : 'POST'
        , data: sendData
        , success :function(resp){
         return postList();
         }
       })
}



function categoryUp(id) {
    var sendData = "id="+id;
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url:'categoryList/up'
        , method : 'POST'
        , data: sendData
        , success :function(resp){
            return categoryList();
            }
    })
}

function categoryDown(id) {
    var sendData = "id="+id;
    //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
    $.ajax({
        url:'categoryList/down'
        , method : 'POST'
        , data: sendData
        , success :function(resp){
         return categoryList();
         }
       })
}




