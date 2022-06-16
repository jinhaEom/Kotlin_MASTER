SharedPreferences
- 간단한 데이터 저장 목적
- 내부 저장소를 사용히가 때문에 권한 설정이 필요없고 간단한코드로 작성 가능
- Intent 에 값을 전달하듯 데이터를 키와 값 쌍으로 저장 가능
- 데이터는 xml 형식으로 된 파일로 저장되고 앱이 종료되어도 남아 있음


값을 저장하는 4단계
- 1: SharedPreferences 생성
- 2: Editor 꺼내기
- 3: putInt(), putString() 메서드로 저장하기
- 4: applu()로 파일에 반영하기

값을 읽어오는 2단계
- 1: SharedPreferences 생성
- 2: getInt(),getString() 메서드로 값 읽어오기

