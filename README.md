# android studio와 flask 통신을 위한 변수명 규칙
** android studio와 flask 서버에서 json을 만들고 가져오는 코드의 변수명은 반드시 통일해주어야 한다. **

현재 안드로이드 파일에서 MainActivitiy는 LoginActivitiy.

### [로그인] - LoginActivity 변수
login_id = 로그인 아이디

login_pw = 로그인 비밀번호

btn_register = 회원가입 화면 이동 버튼

btn_login = 로그인 화면 이동 버튼

btn_tts = TTS 화면 이동 버튼

btn_voice = 목소리 등록 화면 이동 버튼

### [회원가입] - RegisterActivity 변수
input_id = 회원가입 아이디

input_pw = 회원가입 비밀번호

input_name = 회원가입 이름

input_age = 회원가입 나이

btn_birth = 회원가입 생일 날짜 입력 << 아직 미완성

bt_register = 회원가입 버튼

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
