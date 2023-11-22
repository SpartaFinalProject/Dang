#  Dang 
<img src="https://user-images.githubusercontent.com/139092987/272443524-cbf7b049-05fc-4941-9ea4-e7339ff92274.png" width="50%" />


<div align="center">
   <h2>Netflix 인터페이스르 오마주한 앱</h2>
   <p>Youtube Media 8 
   </br>
      Youtube를 이용한 YMD버젼 -  8_8 (그만울조) </p>
   <br>
</div>


## 🔳 팀 소개 및 개요
◼ 팀 소개 - 무난무난한 5명이 모인 팀

◼ 개요 - Youtube Api를 가져와 우리 조 마음대로 꾸민 앱

<table>
   <tr>
    <td align="center"><img src="https://github.com/nsojin.png" width="100"></td>
      <td align="center"><img src="https://github.com/werds7890.png" width="100"></td>
    <td align="center"><img src="https://github.com/wonjun3026.png" width="100"/></td>
      <td align="center"><img src="https://github.com/sunho512.png" width="100"/></td>
      <td align="center"><img src="https://github.com/kwonkyungun.png" width="100"/></td>
   </tr>   
   <tr>
      <td align="center"><a href="https://github.com/nsojin">남소진</a> </td>
      <td align="center"><a href="https://github.com/werds7890">김현걸</a></td>
      <td align="center"><a href="https://github.com/wonjun3026">조원준</a></td>
      <td align="center"><a href="https://github.com/sunho512">정선호</a></td>
      <td align="center"><a href="https://github.com/kwonkyungun">권경운</a></td>
   </tr>
      <tr>
      <td align="center">searchFragment/UI</td>
      <td align="center">detailFragment/UI</td>
      <td align="center">home & hotTopic Fragment/UI</td>
      <td align="center">myVideoFragment/UI</td>
      <td align="center">Main/UI 및 프래그먼트 연결</td>
   </tr>
      <tr>
      <td align="center">myVideo retrofit 연결</td>
      <td align="center">search retrofit 연결</td>
      <td align="center">home retrofit 연결</td>
      <td align="center">hotTopic retrofit 연결</td>
      <td align="center">detail retrofit 연결</td>
   </tr>
</table>

## Figma를 이용한 Wire Frame
<img src="https://user-images.githubusercontent.com/139092987/272431719-25b4ed05-a308-4499-b31c-cbe07595460f.png" width="100%" />

## 페이지별 기능 설명
<details>
    <summary>home</summary>
    <div markdown="1"> 
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 각 비디오 아이템 선택시 Detail로 이동<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ videoAPI를 사용하여  인기 영상 표시<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ categoryAPI를 사용하여 카테고리 리스트 스피너에 표시<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ videoAPI를 사용하여 category 클릭 시 관련 영상 표시<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ channelAPI를 사용하여 category 클릭 시 관련 채널을 표시<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ webview를 사용하여 동영상 재생기능 추가
    </div>
</details>
<details>
    <summary>hotTopic</summary>
    <div markdown="1"> 
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ Youtube api 중 VideoData를 사용<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ data값중 descriptor(설명), title(제목), thumbnail(사진), id(url) 로 사용<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ webview를 사용하여 동영상 재생기능 추가<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 받아온 thumbnail 값 대신 webview에 id값을 넣어 적용<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 버튼 클릭시 디테일 페이지 이동 및 데이터 전달
    </div>
</details>
<details>
    <summary>myVideo</summary>
    <div markdown="1"> 
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 사용자의 개인정보<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 프로필 사진, 이름 등 개인정보 상단에 표시<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ detail에서 좋아요 버튼 클릭 시 보관함으로 저장<br/>
    </div>
</details>
<details>
    <summary>search</summary>
        <div markdown="1"> 
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ Search Api를 사용.<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 검색 창에 검색하여 사용자가 원하는 데이터를 보여줌<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 화면 이동시 fade in, fade out 애니메이션<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 비디오 클릭 시 디테일 프레그먼트로 리사이클러뷰 아이템 데이터들 전송<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 모든 카테고리 별 검색 기능 추가<br/>
        </div>
</details>
<details>
    <summary>detail</summary>
        <div markdown="1"> 
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 각 비디오 아이템 선택시 Detail로 이동하여 선택된 비디오의 상세 정보를 제공<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 좋아요" 버튼 추가: 비디오 상세 정보 아래에 즐겨찾기 추가. 사용자가 버튼을 클릭하면 해당 비디오 정보가 Mypage에 저장<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ My Video 저장: “즐겨찾기"를 누른 비디오 정보는 My Page에서 조회 가능하며 내부에서 "즐겨찾기"를 누른 비디오 목록을 가져와 출력<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ Detail Activity에서 공유 기능 추가 / 해당 버튼 클릭시, Android의 공유 인텐트를 사용하여 다른 앱으로 비디오의 링크를 전송<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ Recycleview를 이용해 연관 동영상 보여주기<br/>
        </div>
</details>

## 프로젝트 업무 관리
Github의 Project의 칸반 보드를 통해 Issue를 생성하고,   
완료 된 Issue는 Pull Request와 연결하여 관리   
[YMd_PJ](https://github.com/orgs/YMedia8/projects/2)

![is](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)
![is](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white)
![is](https://img.shields.io/badge/Netflix-E50914?style=for-the-badge&logo=netflix&logoColor=white)
![is](https://img.shields.io/badge/YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white)


## 영상
https://github.com/YMedia8/YMD/assets/81506621/011a9143-2897-479f-a493-6ac87bac77ef
