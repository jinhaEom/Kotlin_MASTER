## 저작권 무료 이미지 검색기
+ 기능
    - Unsplash Api로부터 사진 가져오기
    - 검색한 사진 다운로드 가능
    - 사진을 배경화면으로 설정 가능
    - 로딩할때 Loading Shimmer를 볼 수 있음

+ 활용 기술
    - Retrofit2
    - Coroutine
    - Glide
    - ShimmerLayout
    - WallpaperManager
+ 새로 알게된 기능
    - 키보드 숨김 : hideSoftInputFromWindow(view windowToken, int flags)
    - Glide 이미지 원형으로 가르기 - circleCrop()
    - 키보드에서 오른쪽 하단버튼 검색으로 바꾸기 : imeOptions 값 -> actionSearch (xml)
    - 글씨에 그림자 효과주기(xml)
    android:shadowColor="#60000000"
                android:shadowDx="1"
                android:shadowDy="1"
                android:shadowRadius="5"
