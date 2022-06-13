아키텍쳐의 장점
 - 일관적인 코드작성
 - 생산성 향상
 - Test 용이성
 - 어플 개발의 방향성을 잡아줌.

일반적 아키텍쳐 종류
1. MVC : Model + View + Controller(activity,fragment 등등)
2. MVP : Model + View(ViewController) + Presenter(View에 넣어줄 데이터를 단순히 추상화)
3. MVVM : Model + View(Viewcontroller)+ ViewModel (단방향 형태의 제어)
4. MVVM + DataBinding
5. MVI : Model + View + Intent
6. MvRx, Flux, Ribs, 등등..


    <img src="https://user-images.githubusercontent.com/84216838/173282590-cbdebe5a-409f-4411-bd6e-a8b0a1d06216.png" width=400 height= 200>
    

    <img src="https://user-images.githubusercontent.com/84216838/173282837-830f1a9c-4d8c-492b-9a02-bdf44b7a78c5.png" width =400 height= 200>

    <img src="https://user-images.githubusercontent.com/84216838/173283013-6ab0c8b5-91b2-42eb-8c7b-d2aac509ad81.png" width = 400 height =200>

## TDD란? 
- Test Driven Development의 약자로 테스트가 개발을 이끌어감을 의미
- 테스트를 먼저 만들고 테스트를 통과하기 위한 것을 짜는 것
- 결정과 피드백 사이에 Gap 을 조절 할 수 있는 테크닉 중 하나

+ TDD가 필요한 이유
    - 에자일과 같이 매우 빠르고 많이 Product 개선이 일어나는 과정에선 어쩔 수 없는 불확실성이 따름
    - 이런 이유로 빠른 Communication PingPong, Feedback과 협력이 필요할 수 밖에 없음
    - TDD 도 마찬가지로 잦은 피드백과 협력을 증진 시킬 수 있는 방법이기에, 1번과 같은 상황에서도 충분히 도움됨

+ TDD 가 필요한 상황
    - 결과가 너무 뻔하면 TDD 개발 필요성 X
    - 자신감 지수가 낮은 경우(처음 시도해보는 개발방식 및 불확실성이 높은 경우)
    - 요구사항이 빈번하게 변경되는 경우
    - 테크니컬한 스펙, 비즈니스 로직이 자주 바뀌는 경우
    - 개발하고 난 이후에 다른 개발자에게 해당 스펙에 대해 인수인계가 필요한 경우

+ TDD가 어려운 이유
    - 개발시간의 증가
    - 일반적인 회사에선 Project 단기적 성과에 집중
    - 전체적인 개발시간을 줄이기보단, 오늘 해야할 업무를 쳐내기 바쁨

    - 일반적인 개발 방식과 반대로 가져가야함(테스트 -> 개발)
    - 체득한 것을 내려놓기 어려움

+ TDD를 효율적으로 하는 법
    - 피드백을 자주 받을 수 있는 환경
    - 내가 하는 작업에 협력을 할 수 있는 방법으로 유도

+ TDD 구현 과정
    - 비즈니스 로직을 분리할 수 있는 클린 아키텍쳐 Basecamp 구축
    - Unit test 구현을 위한 도구 도입
    - 각 Unit Test file 생성 및 목적에 맞는 시나리오 작성
    - 시나리오에 필요한 UseCase 작성, 각 레이어 구축(Repo, Model 등)
    - 각 상태를 State Pattern으로 표현해 쉬운 방법으로 결과 검증