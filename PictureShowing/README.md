## 전자 액자 어플
    - 저장소 접근 권한을 이용하여 로컬 사진을 로드할 수 있음.
    - 추가한 사진들을 일정한 간격으로 전환하여 보여줄 수 있음.
## 요구된 기술
    -Android Permission : 사용자로 하여금 권한을 얻어올 수 있는 기능.(이 앱에선 사진가져오기 권한)
                            ->manifest에도 permission 꼭 추가하여야함.
    -View Animation - 이 앱에선 화면이 전환될 떄 alpha값을 0에서 1로 주는 것으로 사용.
                    ->animate 함수를 통해 페이드인 모션 추가.
    -Content Provider - Storage Access Framework (SAF) 기능을 활용해 사진을 추가

    -android:screenOrientation="landscape"을 통해 가로화면으로 볼 수 있음.


<img src="https://user-images.githubusercontent.com/84216838/148328199-373fe603-5ab8-435a-9218-e25ea3eb6e61.png"  width="200" height="400"/>
<img src="https://user-images.githubusercontent.com/84216838/148328295-74a32b24-05d7-4015-ace1-7fef5f55ce86.png"  width="200" height="400"/>
<img src="https://user-images.githubusercontent.com/84216838/148328296-0a52a7cb-7a6e-47be-bc84-2fca6f95f83c.png"  width="200" height="400"/>
<img src="https://user-images.githubusercontent.com/84216838/148328411-d09745d1-2d45-4055-8a2b-39458d404da5.png"  width="200" height="400"/>



