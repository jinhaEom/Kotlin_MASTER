## API를 활용한 베스트셀러 책 리뷰 어플
+ '인터파크 도서' API(bestseller, search)를 가져와 화면에 띄우고 검색하는 기능.

   * 요구된 기술
        - 서버와 클라이언트 간 http 통신을 위해 'retrofit' 라이브러리 사용.
        - json구조를 띄는 직렬화된 데이터를 객체로써 직렬화해주는 GSON 사용.
        - url형태로 넘겨받은 이미지값을 실제이미지로 로드하기 위해 glide 라이브러리 사용.
    * 개선점
        - 책에 대한 간단한 리뷰를 남길수 있는 공간과 그 리뷰를 저장하는 버튼을 구현하였지만
          버전의 차이로 인해 오류 발생

<img src="https://user-images.githubusercontent.com/84216838/150922678-c3af767e-5154-4617-9d0f-ea7560c68168.png" width=200 height=400/>
<img src="https://user-images.githubusercontent.com/84216838/150922684-944ccbfd-5b23-4d87-9906-ba9bf5a12bac.png" width=200 height=400/>
<img src="https://user-images.githubusercontent.com/84216838/150922690-30fe7c40-bf6c-47d1-8ac2-6f50452d8ac2.png" width=200 height=400/>
<img src="https://user-images.githubusercontent.com/84216838/150923034-03bf07ac-231c-46ec-923f-ff28fa4a1737.png" width=200 height=400/>


