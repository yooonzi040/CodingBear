# android studio와 flask 통신을 위한 변수명 규칙
** android studio와 flask 서버에서 json을 만들고 가져오는 코드의 변수명은 반드시 통일해주어야 한다. **

현재 안드로이드 파일에서 MainActivitiy는 LoginActivitiy.

### [로그인] - LoginActivity 변수
input_loginName = 로그인 이름

input_loginBirth = 로그인 생년월일

input_loginPhoneNo = 로그인 전화번호

btn_voice = 목소리 등록 화면 이동 버튼

btn_tts = TTS 화면 이동 버튼

btn_register = 회원가입 화면 이동 버튼

btn_login = 로그인 성공 시 메인엑티비티 이동 버튼

btn_settings = 설정 화면 이동 버튼 (테스트용)

btn_main = 메인 화면 이동 버튼 (테스트용)

### [로그아웃] - LogoutActivity 변수


### [회원가입] - RegisterActivity 변수
input_Name = 회원가입 이름

input_PhoneNo = 회원가입 전화번호

input_Birth = 회원가입 생년월일

input_guardianName = 회원가입 보호자 이름

input_guardianPhoneNo = 회원가입 보호자 전화번호

bt_register = 회원가입 버튼

### [메인화면] - MainActivity 변수
btn_main_settings = 설정버튼

btn_main_Logout = 로그아웃 버튼

### [TTS 화면] - VoiceChatBotActivity 변수
btn_sttStart = STT 시작 버튼

btn_send = 사용자 텍스트 입력 전송 버튼

userInputEditText = 사용자 텍스트 입력 창

### [TTS를 위한 목소리 등록 화면] - VoiceInput01Activitiy 변수 (02, 03은 btn만 구현되어 있음)
btn_record = 녹음 시작 버튼 // 녹음 중 한번 더 누르면 정지 버튼

btn_repeat = 재녹음 시작 버튼

btn_check = 녹음된 음성 재생 버튼

btn_next = 다음 목소리 등록 화면 이동 버튼

mediaRecorder = 안드로이드 녹음 라이브러리

audioFileName = 오디오 녹음 생성 파일 이름

recording = false // 현재 녹음 중인지 확인용

audioUrl = null // 오디오 파일 URL

mediaPlayer = 안드로이드 재생 라이브러리 playing = false; // 현재 재생 중인지 확인용