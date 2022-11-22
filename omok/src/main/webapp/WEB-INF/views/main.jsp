<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
	$(function(){
		for (var j = 0; j < 19; j++) {
			$("#boardMain").append("<div class='frame' name='frame" + j + "'></div>")
			for (var i = 0; i < 19; i++) {
				$("div[name=frame" + j + "]").append("<div class='cell' id=" + (19*j + i) + "></div>")
			}
		}
		
		$("input[name='insert']").attr("disabled", true);
		$("input[name='delete']").attr("disabled", true);
		$(".s_button").attr("disabled", true);
		$("input[name='logout']").attr("disabled", true);
		$("input[name='surrender']").attr("disabled", true);
		
		jbtnOn($("input[name='reqid']"));		
		
		$('.s_button').on({
			click: function(){
				$.ajax({
					url: "./start",
					dataType:'json',
					type: "POST",
					success: function(game){
						start();
						$("#stoneImgBox"+game.white.loginPlayer).empty();
						$("#stoneImgBox"+game.black.loginPlayer).empty();
						$("#stoneImgBox"+game.white.loginPlayer)
							.append("<img id='stoneImg' src='./resources/images/white.png'>");
						$("#stoneImgBox"+game.black.loginPlayer)
							.css("background-color", "#cfcfcf")
							.append("<img id='stoneImg' src='./resources/images/black.png'>");
						console.log(game);
					}
				})
			}
		})
		
		$('#l_button').on({
			click: function(){
				login();
			}
		})
		
		$("input[name='insert']").on({
			click: function(){
				insert();
			}
		})
		
		$("input[name='logout']").on({
			click: function(){
				logout(this);
			}
		})

		$("input[name='surrender']").on({
			click: function(){
				surrender(this);
			}
		})
		
		 $("input[name='delete']").on({
            click: function(){
                deletePlayer();
            }
        })
		
	})
	
	function deletePlayer(){
        $.ajax({
            url: "./player/deletePlayer",
            dataType:'json',
            contentType: "application/json; charset=utf-8",
            type: "post",
            data: JSON.stringify({
                id: $("input[name='reqid']").val()
            }),
            success: function(player){
                $("#msg").html(player.msg);
            }
        })
	}

	function logout(obj){
		$.ajax({
			url: "./player/logout",
			dataType:'json',
			contentType: "application/json; charset=utf-8",
			type: "post",
			data: JSON.stringify({
				loginPlayer: $(obj).parent().find("#player").val()
			}),
			success: function(player){
				$(obj).parent().find("p[name='status']").html('전적');
				$(obj).parent().find("p[name='pid']").html('USER');
				$(obj).parent().find("img").remove();
				$(obj).attr("disabled", true);
				$('.s_button').attr("disabled", !player.ready);
				console.log(player.ready);
				alert(player.msg);
			}
		})
	}
	
	function insert(){
		$.ajax({
			url: "./player/insertPlayer",
			dataType:'json',
			contentType: "application/json; charset=utf-8",
			type: "post",
			data: JSON.stringify({
				id: $("input[name='reqid']").val()
			}),
			success: function(player){
				$("#msg").html(player.msg);
			}
		})
	}
	
	function login(){
		$.ajax({
			url: "./player/join",
			dataType:'json',
			contentType: "application/json; charset=utf-8",
			type: "post",
			data: JSON.stringify({
				id: $("input[name='loginid']").val(),
				loginPlayer: $("#loginPlayer").val()
			}),
			success: function(player){
				if (player.loginPlayer != 0){
					$("#stoneImgBox"+player.loginPlayer).empty();
					$("#userMain"+player.loginPlayer+">p[name='status']").html(player.victory+"승 "+player.defeat+"패  "+player.winRate+"%");
					$("#userMain"+player.loginPlayer+">p[name='pid']").html(player.id);
					$("#userMain"+player.loginPlayer).find("input[name='logout']").attr("disabled", false);
					$('.s_button').attr("disabled", !player.ready);
				} else alert(player.msg);
				console.log(player.ready);
			},
			error :function(res) {
				console.log(res);
			}
		})
	}
	
	function end(game){
		$("#userMain"+game.white.loginPlayer+">p[name='status']").html(game.white.victory+"승 "+game.white.defeat+"패  "+game.white.winRate+"%");
		$("#userMain"+game.white.loginPlayer+">p[name='pid']").html(game.white.id);
		$("#userMain"+game.black.loginPlayer+">p[name='status']").html(game.black.victory+"승 "+game.black.defeat+"패  "+game.black.winRate+"%");
		$("#userMain"+game.black.loginPlayer+">p[name='pid']").html(game.black.id);
		$("#stoneImgBox"+game.white.loginPlayer).css("background-color", "");
		$("#stoneImgBox"+game.black.loginPlayer).css("background-color", "");
		$(".cell").off();
		$('.s_button').attr("disabled", false);
		$("#l_button").attr("disabled", false);
		$("input[name='surrender']").attr("disabled", true);
		$("input[name='logout']").attr("disabled", false);
		jbtnOn($("input[name='reqid']"));
	}
	
	function surrender(obj){
		$.ajax({
			url: "./surrender",
			dataType:'json',
			contentType: "application/json; charset=utf-8",
			type: "POST",
			data: JSON.stringify({
				loginPlayer: $(obj).parent().find("#player").val()
			}),
			success: function(game){
				console.log(game);
				alert(game.msg);
				end(game);
			}
		})
	}
	
	function jbtnOn(obj){
		$(obj).on("propertychange change keyup paste input", function(){
			if ($(obj).val() === ""){
				$("input[name='insert']").attr("disabled", true);
				$("input[name='delete']").attr("disabled", true);
			} else {
				$("input[name='insert']").attr("disabled", false);
				$("input[name='delete']").attr("disabled", false);
			}
		});
	}
	
	function start(){
		$(".s_button").attr("disabled", true);
		$("input[name='insert']").attr("disabled", true);
		$("input[name='delete']").attr("disabled", true);
		$("#l_button").attr("disabled", true);
		$("input[name='logout']").attr("disabled", true);
		$("input[name='surrender']").attr("disabled", false);
		$(".cell").empty();
		$("input[name='reqid']").off();
		$(".cell").on({
			
			mouseenter: function(){
				$(this).css(
					"border", "2px solid red"	
				)},
				
			mouseleave: function(){
				$(this).css("border","")},
				
			click: function(){
				var no = $(this).attr('id');
				$.ajax({
					url: "./putStone",
					data: JSON.stringify({
						colNo : no % 19,
						rowNo : no / 19,
						}),
					contentType: "application/json; charset=utf-8",
					dataType: "json",
					type: "post",
					success: function(game){
						console.log(game);
						if (game.turnCnt % 2 == 0) {
							$('#'+no).append("<img id='stone' src='./resources/images/black.png'>");
							$("#stoneImgBox"+game.black.loginPlayer).css("background-color", "");
							$("#stoneImgBox"+game.white.loginPlayer).css("background-color", "#cfcfcf");
						} else {
							$('#'+no).append("<img id='stone' src='./resources/images/white.png'>");
							$("#stoneImgBox"+game.black.loginPlayer).css("background-color", "#cfcfcf");
							$("#stoneImgBox"+game.white.loginPlayer).css("background-color", "");
						}
						$('#'+no).find('img').on('load', function() {
					    	if (game.winner == 0) {
					    		alert(game.black.id + "님이 승리했습니다!");
								end(game);
					    	} else if (game.winner == 1) {
					    		alert(game.white.id + "님이 승리했습니다!");
								end(game);
					    	}
					    });
						$('#'+no).css("border","");
						$('#'+no).off("mouseenter");
						$('#'+no).off("mouseleave");
						$('#'+no).unbind('click');
					},
					error :function(res) {
						console.log(res);
					}
				})
			}
		})
		
	}
</script>
<title>오목</title>

<!-- 폰트링크 -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gugi&display=swap" rel="stylesheet">

<style>

	/* 폰트 */
	
	.font_normal{
		font-family: 'Do Hyeon', sans-serif;
		font-size:20px;
		margin: 0 auto;
	}
	.font_omok{
		font-family: 'Gugi', cursive;
		font-size:100px;
		margin: 0 auto;
	}
	
	/* 수평배치 */
	
	body{
		margin: 0 auto;
    	padding: 0;
	}
	div.container{
    	overflow: hidden;
    	margin-top: 10px;
		margin-right: 10px;
   		margin-left: 10px;
    }
	div.item{
    	float:left;
    }
    
    /* 로그인 */
    
    #loginMain{
    	width: 280px;
      	height:100px;
      	border: 5px solid #F9D85D;
      	border-radius:30px;
      	background-color: #FFFFFF;
      	margin-left:20px;
      	padding-top:7px;
      	padding-bottom:7px;
      	padding-left:3px;
      	padding-right:3px;
   	}
   	
    #loginImg{
      	width: 80px;           
      	height:80px;
      	display:block;
      	margin:0 auto;

   	}
   	
   	#loginInput{
      	width: 200px;           
      	height:80px;
   	}
   	
   	#loginName{
   		width: 105px;
   		height: 20px;
   		margin: 1px;
   	}
   	
   	#loginPlayer{
   		width: 113px;
   		height: 20px;
   		margin: 1px;
   	}
   	
   	#l_button{
   		border: 2px solid #F9D85D;
   		background-color: #F9D85D;
   		border-radius:5px;
   		color:white;
   		font-size:20px;
   		font-family:'Do Hyeon', sans-serif;
   	}
   	#l_button:active{
   		background-color: #F7CC22;
   	}
   	#l_button:disabled{
   		background: #c9c9c9;
		border: #FFFFFF;
   	}
   	
   	/* 회원 가입 */
   	
   	#joinMain{
   		width: 280px;
      	height:100px;
      	border: 5px solid #F3B176;
      	border-radius:30px;
      	background-color: #FFFFFF;
      	margin-left:10px;
      	padding-top:7px;
      	padding-bottom:7px;
      	padding-left:3px;
      	padding-right:3px;
   	}
   	
   	#j_button{
   		border: 2px solid #F3B176;
   		background-color: #F3B176;
   		border-radius:5px;
   		color:white;
   		font-size:20px;
   		font-family:'Do Hyeon', sans-serif;
   	}
  	#j_button:active{
   		background-color: #E98E45;
   	}
   	#j_button:disabled{
   		background: #c9c9c9;
		border: #FFFFFF;
   	}
   	
   	
   	/* 보드 판 */
   	
   	#boardMain{
   		width: 570px;
      	height: 570px;
      	border: 5px solid #B88927;
      	border-radius:10px;
      	background-color: #DCB35C;
      	margin-left:5px;
      	padding:5px;
	   	background-image : url('./resources/images/board.png');
	   	background-size: 100% 100%;
	   	z-index: 1;
   	}
   	
   	/* 게임 시작 */
   	
   	.s_button{
   		border: 2px solid #F18872;
   		background-color: #F18872;
   		border-radius:10px;
   		color:white;
   		font-size:35px;
   		font-family:'Do Hyeon', sans-serif;
   		margin-left:10px;
   		padding-top:2px;
   	}
   	.s_button:active{
   		background-color:#ED684B;
   	}
   	.s_button:disabled{
   		background-color: #c9c9c9;
		border: #FFFFFF;
   	}
   	
   	/* 대전 중인 유저 정보 */
   	
    #userMain1{
        width: 165px;
        height:250px;
        border: 5px solid #99D9EA;
        border-radius:30px;
        background-color: #FFFFFF;
        margin-left:10px;
        margin-top:10px;
    }
    #userMain2{
        width: 165px;
        height:250px;
        border: 5px solid #C8BFE7;
        border-radius:30px;
        background-color: #FFFFFF;
        margin-left:10px;
        margin-top:10px;
    }
   	
   	#stoneImgBox1{
    	width: 90px;
      	height:90px;
      	border: 5px solid #99D9EA;
      	border-radius:30px;
      	background-color: #FFFFFF;
      	margin:2px auto;
       	display:block;
   	}
   	
   	#stoneImgBox2{
    	width: 90px;
      	height:90px;
      	border: 5px solid #C8BFE7;
      	border-radius:30px;
      	background-color: #FFFFFF;
      	margin:2px auto;
       	display:block;
   	}
   	
   	#stoneImg{
   		width: 90px;
      	height:90px;
   	}
   	
   	#font1{
   		color:#99D9EA;
   		text-align:center;
   		font-size:30px;
   	}
   	
   	#font2{
   		color:#C8BFE7;
   		text-align:center;
   		font-size:30px;
   	}
   	
   	#font3{
   		color:#99D9EA;
   		text-align:center;
   		font-size:20px;
   	}
   	
   	#font4{
   		color:#C8BFE7;
   		text-align:center;
   		font-size:20px;
   	}
   	
   	#u_button{
   		border: 2px solid #99D9EA;
   		background-color: #99D9EA;
   		border-radius:5px;
   		color:white;
   		font-size:20px;
   		font-family:'Do Hyeon', sans-serif;
   		margin:3px auto;
       	display:block;
   	}
   	
   	#u_button:active{
   		background-color:#6CCAE1;
   	}
 	#u_button:disabled{
   		background: #c9c9c9;
		border: #FFFFFF;
   	}
   	
   	#u_button2{
   		border: 2px solid #C8BFE7;
   		background-color: #C8BFE7;
   		border-radius:5px;
   		color:white;
   		font-size:20px;
   		font-family:'Do Hyeon', sans-serif;
   		margin:3px auto;
       	display:block;
   	}
   	#u_button2:active{
   		background-color:#AD9FDB;
   	}
   	#u_button2:disabled{
   		background: #c9c9c9;
		border: #FFFFFF;
   	}
   	.cell {
		float : left;
		width : 26px;
		height : 26px;
		border : 2px solid rgba(0,0,0,0);
		z-index: 10;
	}
	
	#stone {
		width: 26px;
		height: 26px;
	}

</style>

</head>
<body>
   <div class="container">
   	  <div class="item">
   	  	<p class="font_omok">오목</p>	
   	  </div>
   	  <div class="item" id= "loginMain">
   	  		<form action="./player/join" method="post">
   	  			<table>
   	  				<tr>
   	  					<td rowspan="3"><img alt="user" src="./resources/images/userImg1.png" width="80" height="80"></td>
   	  					<td><label for="name" class="font_normal">이름</label></td>
      					<td><input id="loginName" type="text" name="loginid"></td>
   	  				</tr>
   	  				<tr>
   	  					<td><label for="sel_player" class="font_normal">플레이어</label></td>
   	  					<td>
   	  						<select id="loginPlayer" name="loginPlayer">
   	  							<option value="1">Player1</option>
                        		<option value="2">Player2</option>  
   	  						</select>
   	  					</td>
   	  				</tr>
   	  				<tr>
   	  					<td></td>
   	  					<td>
   	  						<input type="button" value="로그인" id="l_button" style="width:115px;height:30px;">
   	  					</td>
   	  				</tr>	
   	  			</table>
   	  		</form>
   	  	</div>
   	  	<div class="item" id= "joinMain">
   	  		<form>
   	  			<table>
   	  				<tr>
   	  					<td rowspan="3"><img alt="user" src="./resources/images/joinImg.png" width="80" height="80"></td>
   	  					<td><label for="name2" class="font_normal">이름</label></td>
      					<td><input id="loginName" type="text" name="reqid"></td>
   	  				</tr>
   	  				<tr>
   	  					<td><span class="font_normal">메세지:</span></td>
   	  					<td><span class="font_normal" id="msg"></span></td>
   	  				</tr>
   	  				<tr>
   	  					<td></td>
   	  					<td>
   	  						<input type="button" value="등록" id="j_button" name="insert" style="width:55px;height:30px;">
   	  						<input type="button" value="삭제" id="j_button" name="delete" style="width:55px;height:30px;">
   	  					</td>
   	  				</tr>	
   	  			</table>
   	  		</form>
   	  	</div>				
   	 </div>
   	 <div class="container">
   	 	<div class="item" id="boardMain">
   	 		<!-- <img alt="board" id="board" src="./resources/images/board.png"> -->
   	 	</div>
   	 	<div class="item">
   	 		<input type="button" value="게임시작" class="s_button" disabled="disabled" style="width:170px;height:50px;">
   	 		<div id="userMain1">
   	 			<p class="font_normal" id="font1" name="pid">USER1</p>
   	 			<div id="stoneImgBox1">
   	 			</div>
   	 			<p class="font_normal" id="font3" name="status">전적</p>
   	 			<input type="button" value="로그아웃" id="u_button" name="logout" style="width:130px;height:30px;">
   	 			<input type="button" value="기권" id="u_button" name="surrender" style="width:130px;height:30px;">
   	 			<input type="hidden" value="1" id="player">
   	 		</div>
   	 		<div id="userMain2">
   	 			<p class="font_normal" id="font2" name="pid">USER2</p>
   	 			<div id="stoneImgBox2">
   	 			</div>
   	 			<p class="font_normal" id="font4" name="status">전적</p>
   	 			<input type="button" value="로그아웃" id="u_button2" name="logout" style="width:130px;height:30px;">
   	 			<input type="button" value="기권" id="u_button2" name="surrender" style="width:130px;height:30px;">
   	 			<input type="hidden" value="2" id="player">
   	 		</div>
   	 	</div>
   	 </div>
</body>
</html>

