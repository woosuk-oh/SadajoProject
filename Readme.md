﻿* 순서
 ADD - COMMIT - PUSH or PULL

1. CMD 터미널에서 프로젝트 위치를 cd 로 이동
2. git add .
3. git commit -m "메세지 입력"
4. git push

변동 사항이 있으면 무조건 add와 commit을 먼저 해줘야됨  


-------------------

레파지토리 만들면
branch에서 master로 create 한번 해주어야 push가 된다

* GUI 사용법
 1. 변동사항 발생
 2. Rescan
 3. Stage Changed (해당 경로에 파일들이 추가 되면 Untracked되어, Track상태로 변경해야 하므로 cmd에서 하려면 add시켜준다)
 4. 메세지 입력 후 커밋
 5. push
