#  Dang 
<img src="https://user-images.githubusercontent.com/139092987/272443524-cbf7b049-05fc-4941-9ea4-e7339ff92274.png" width="50%" />


<div align="center">
   <h2>유기견 정보 검색 앱</h2>
   <p>Dang - 유기견 정보 검색 앱 
   </br>
      공공데이터 포털을 이용한 유기견 앱 -  7조 </p>
   <br>
</div>


## 🔳 팀 소개 및 개요
◼ 팀 소개 - 무난무난한 5명이 모인 팀

◼ 개요 - 유기동물 정보 조회 Api를 가져와 다양한 유기견을 소개해주는 앱

<table>
   <tr>
    <td align="center"><img src="https://github.com/nsojin.png" width="100"></td>
      <td align="center"><img src="https://github.com/werds7890.png" width="100"></td>
    <td align="center"><img src="https://github.com/wonjun3026.png" width="100"/></td>
      <td align="center"><img src="https://github.com/sunho512.png" width="100"/></td>
      <td align="center"><img src="https://github.com/kwonkyungun.png" width="100"/></td>
   </tr>   
   <tr>
      <td align="center"><a href="https://github.com/nsojin">소준선</a> </td>
      <td align="center"><a href="https://github.com/werds7890">권용일</a></td>
      <td align="center"><a href="https://github.com/wonjun3026">조원준</a></td>
      <td align="center"><a href="https://github.com/sunho512">김현정</a></td>
      <td align="center"><a href="https://github.com/kwonkyungun">이예원</a></td>
   </tr>
      <tr>
      <td align="center">homeFragment</td>
      <td align="center">shelterFragment/UI</td>
      <td align="center">searchFragment</td>
      <td align="center">pretest/UI</td>
      <td align="center">Main/UI</td>
   </tr>
      <tr>
      <td align="center">댕찜 페이지 구현</td>
      <td align="center">카카오 맵 구현</td>
      <td align="center">디테일 페이지 구현</td>
      <td align="center">댕댕백과 페이지 구현</td>
      <td align="center">프래그먼트 연결</td>
   </tr>
</table>

## Figma를 이용한 Wire Frame
<img src="https://user-images.githubusercontent.com/139092987/272431719-25b4ed05-a308-4499-b31c-cbe07595460f.png" width="100%" />

## 페이지별 기능 설명
<details>
    <summary>home</summary>
    <div markdown="1"> 
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 상단 앱 바 검색 아이콘 버튼 클릭시 “댕찾기”로 이동<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 상단 배너  “See more” 버튼 클릭시 “댕지킴이 “or”댕찾기”로 이동<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ Recycle View를 이용하여 현재 공고중인 유기견에 대한 정보 아이템으로 표시<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 아이템 클릭시 디테일 페이지로 이동합니다.<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ navigationbar 아이콘 클릭시 각 액티비티로 이동
    </div>
</details>
<details>
    <summary>Shelter</summary>
    <div markdown="1"> 
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 시,도 or 시,군,구 선택 완료시 하단맵에 지정한 위치 기반으로 주변 보호소 위치를 마커로 표시<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 맵에 표시된 마커 터치시 뱃지로 간략한 정보 표시후 하단 정보 창에 해당 보호소의 정보 제공<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 하단 정보창 “선택하기” 클릭시 해당 보호소에서 보호중인 견종들에 대한 정보를 제공<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 아이템 클릭시 디테일 페이지로 이동합니다. <br/>
    </div>
</details>
<details>
    <summary>Search</summary>
    <div markdown="1"> 
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 검색 기록을 베이스로 최근 검색 리스트를나타냅니다.<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 품 종 검색어 입력시 추천 자동 완성 텍스트를 보여줍니다.<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 검색후 해당하는 유기견 아이템들이  Recycle View로 나타내어지고 검색 창 하단에 나이,성별,크기로 지정할수있는 필터 버튼이 보이고 클릭시 하단에 Dialog창이 나옵니다.<br/>
       &nbsp;&nbsp;&nbsp;&nbsp; ▪️ Dialog에 값을 직접 입력할 수 있는 창이 있으며 아래에 완성되있는 버튼 입력시 지정된 입력값이 자동으로 입력창에 입력이 됩니다. <br/>
       &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 적용하기 버튼 클릭시 상단 필터 버튼에 적용한 값에 대한 정보가 띄워지고 Recycle View에 해당 필터에 해당하는 아이템들로 다시 정렬됩니다.<br/>
       &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 아이템 클릭시 디테일 페이지로 이동합니다.<br/>
    </div>
</details>
<details>
    <summary>댕댕백과&댕</summary>
        <div markdown="1"> 
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 상단 검색창에 강아지의 품종을 검색 시 검색창 하단에 해당하는 품종에 대한 간략한 정보를 제공하는 아이템을 나타냅니다. <br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 디테일 페이지에서 “하트 아이콘” 클릭시 해당 아이템은 보관함 페이지에도 따로 저장 됩니다.(SharedPreferences 사용)<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 해당 아이템을 누른 상태로 옆으로 슬라이딩시 아이템이 삭제됩니다.<br/>
        </div>
</details>
<details>
    <summary>detail</summary>
        <div markdown="1"> 
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ Dang,댕지킴이,댕찾기,댕찜 페이지에서 아이템 클릭시 디테일 페이지로 이동합니다.<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 해당 유기견의 품종,등록번호,발견장소,특징 등 디테일한 정보를 제공합니다.<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 해당 유기견을 보호하고 있는 보호센터의 정보를 제공합니다.<br/>
        &nbsp;&nbsp;&nbsp;&nbsp; ▪️ 하단 “보호소 연락하기” 버튼 클릭시 해당 유기견을 보호하고 있는 보호센터의 전화번호가 자동으로 입력이 된 상태로 기본 전화앱을 활성화 합니다. <br/>
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
