## 서비스란?
	화면이 없는 Activity(메인 Thread 사용)

+ 서비스의 실행 방식
	- Started Service
	- Bound Service
	- Foreground Service( 앱이 꺼져도 실행되는 서비스)

+ Started Service
	- startService() 메소드로 호출, Activity와 상관없이 독립적으로 동작

+ Bound Service
	- bindService() 메소드로 호출, Activity와 값을 주고받을 필요가 있을 때 사용
	- 여러 개의 Activity가 같은 서비스를 사용 할 수 있어서 기존에생성되어 있는 Service 를 binding 해서 재사용 가능
	- Activity와 값을 주고받을 필요가 있을 때 사용하고 값을 주고받기 위한 interface 제공
	

