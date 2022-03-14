[infilix]
  : 온라인 스트리밍 서비스를 제공을 위한 서버를 구현중입니다.
  
 - 구성도


![image](https://user-images.githubusercontent.com/14008231/158085231-988600a5-743e-4eb5-a58c-1b4620d25757.png)

위 구성도 중 Backend부분이며 각 서비스는 Docker의 Container 환경에서 운영됩니다.
각 서비스의 EndPoint 제공을 위하여 Spring Cloud Gateway 적용하였습니다.
