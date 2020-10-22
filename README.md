# Code Market
<br/>

<https://github.com/n0y0j/shopping_mall>
<br/>

**이 프로젝트는 국민대학교 모바일 프로그래밍 강의를 바탕으로 제작하였습니다.**<br/>
프로그래밍 언어를 상품으로 판매하는 쇼핑몰 모바일 어플리케이션이다. 이 어플리케이션은 상품을 선택할 수 있는 "홈", 원하는 상품을 저장해놓을 수 있는 "장바구니", 상품을 구매할 수 있는 "구매" 총 3개의 Activity로 이루어져 있으며 Android Studio에서 Java와 FireStore를 사용해 직접 구현했다.
<br/>

![캡처](https://user-images.githubusercontent.com/28584258/96588644-e234f480-131e-11eb-8aa3-b4cadaddd860.PNG)
<br/>

***

# IntroActivity
<br/>

App 실행 시 IntroActivity가 2초 지속 후 MainActivity 실행
<br/>

![Screenshot_1603198550](https://user-images.githubusercontent.com/28584258/96588824-0db7df00-131f-11eb-8512-b540850418af.png)
<br/>

***

# MainActivity (상품 선택)
<br/>

사용률이 높은 1~20위 프로그래밍 언어를 Crawling 후 RecyclerView를 사용해 Item(CardView) 생성
* 참조 : <https://www.tiobe.com/tiobe-index/>
<br/>

![Screenshot_1603197167](https://user-images.githubusercontent.com/28584258/96589802-3b515800-1320-11eb-9e10-c12ed1fa6d86.png)
![Screenshot_1603197185](https://user-images.githubusercontent.com/28584258/96589811-3c828500-1320-11eb-85cd-72e3ac642b50.png)
<br/>

Item의 선택
 * 선택된 CardView의 BackgroundColor가 변함 (다른 Item과 구별하기 위함)
 * Bottom LinearLayout 표시 (장바구니 버튼, 구매 버튼)
 <br/>

![Screenshot_1603197193](https://user-images.githubusercontent.com/28584258/96591392-2c6ba500-1322-11eb-8c0d-b2c3a3f96360.png)
<br/>

장바구니 버튼 클릭 시 모습
 * 장바구니 버튼을 클릭하는 순간 장바구니에 Item을 삽입
 * DialogAlert의 아니오 클릭 시 MainActivity에 머무름
 * DialogAlert의 예 클릭 시 FavoriteActivity로 이동
 <br/>

![Screenshot_1603197197](https://user-images.githubusercontent.com/28584258/96591746-8c624b80-1322-11eb-9a20-d1b654a77b25.png)
![Screenshot_1603197229 - 복사본](https://user-images.githubusercontent.com/28584258/96592717-a8b2b800-1323-11eb-8d04-34b15258eb48.png)
<br/>

구매 버튼 클릭 시 Item을 구매하기 위해 BuyActivity로 이동
<br/>

***

# FavoriteActivity (장바구니)
<br/>

MainActivity (상품 선택 페이지)에서 장바구니 버튼을 클릭해 저장된 Item을 출력
  * 홈 버튼 클릭 시 MainActivity로 이동
  * 구매 버튼 클릭 시 각 Item의 CheckBox가 Check되있는 Item들만 BuyActivity로 이동
<br/>

![Screenshot_1603197210](https://user-images.githubusercontent.com/28584258/96593117-1fe84c00-1324-11eb-8d4c-b4aef8bc5efe.png)
<br/>

장바구니에 Item이 여러개 있는 경우
<br/>

![Screenshot_1603197229 - 복사본 - 복사본](https://user-images.githubusercontent.com/28584258/96593344-5b831600-1324-11eb-8c69-8466824ae3e8.png)
![Screenshot_1603197235](https://user-images.githubusercontent.com/28584258/96593350-5cb44300-1324-11eb-9d30-c50415b0a522.png)
<br/>

Item들이 Check되있는 모습
<br/>

![Screenshot_1603197243](https://user-images.githubusercontent.com/28584258/96593808-e2d08980-1324-11eb-8801-60dda1b8c45b.png)
<br/>

***

# BuyActivity (구매)
<br/>

MainActivity에서 구매 버튼을 클릭해 가져온 한 개의 Item을 출력
FavoriteActivity에서 Item의 Check 여부를 통해 저장된 여러개의 Item을 출력
 * 구매 버튼 클릭 시 Toast Message 생성 후 MainActivity로 이동
<br/>

![Screenshot_1603201262](https://user-images.githubusercontent.com/28584258/96594288-6ab69380-1325-11eb-8380-79026e1824be.png)
![Screenshot_1603197249](https://user-images.githubusercontent.com/28584258/96594294-6be7c080-1325-11eb-83cc-fe2040ec4997.png)
<br/>

Item의 Check여부에 따라 TotalPrice(합계)가 달라진다
<br/>

![Screenshot_1603201433](https://user-images.githubusercontent.com/28584258/96594693-dbf64680-1325-11eb-9d8c-f4220407a352.png)
![Screenshot_1603197258](https://user-images.githubusercontent.com/28584258/96594696-dd277380-1325-11eb-9323-b8ed1d23ed97.png)
![Screenshot_1603201436](https://user-images.githubusercontent.com/28584258/96594703-de58a080-1325-11eb-8b57-6885ac6f1f95.png)
<br/>

구매 할 Item을 선택 후 주소와 전화번호 작성
 * 작성하지 않을 시 Toast Message 생성
<br/>
  
![Screenshot_1603197290](https://user-images.githubusercontent.com/28584258/96594938-1bbd2e00-1326-11eb-9f0b-d7602f1a8013.png)
<br/>
 
