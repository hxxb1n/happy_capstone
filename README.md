<div align="center">
  <img src="assets/logo.webp" alt="쇼핑몰 로고" />
</div>

## IoT 기반 쇼핑몰 배달 시스템

### 프로젝트 개요
우리의 프로젝트는 소비자가 쇼핑몰에서 물건을 주문하면 각 주문마다 고유한 바코드가 생성되는 IoT 기반 배달 시스템입니다. 배달기사는 이 바코드를 이용하여 출입문을 통해 출입할 수 있습니다. 이 시스템은 소비자와 배달기사 모두에게 편리하고 안전한 서비스를 제공하는 것을 목표로 합니다.

### 주요 기능

1. **소비자 기능**:
   - 회원 가입, 로그인
   - 쇼핑몰에서 상품 주문
   - 주문 내역 확인
   - 주문 상태 추적

2. **배달기사 기능**:
   - 주문 목록 확인
   - 각 주문에 대한 고유 바코드 스캔
   - 출입문 접근 제어를 위한 바코드 사용

3. **관리자 기능**:
   - 사용자 및 주문 관리
   - 시스템 모니터링 및 로그 확인

### 시스템 구성

1. **프론트엔드**:
   - 안드로이드 앱으로 소비자와 배달기사의 인터페이스 제공
   - 쇼핑몰 상품 검색, 주문, 배달 정보 확인 및 바코드 스캔 기능 구현

2. **백엔드**:
   - Spring Boot 기반 서버로 사용자 인증, 주문 처리, 바코드 생성 및 검증 기능 제공
   - MySQL 데이터베이스와 연동하여 사용자, 주문, 배달 정보를 관리

3. **IoT 장치**:
   - 바코드 스캐너와 출입문 제어 장치로 구성
   - 배달기사가 바코드를 스캔하면 출입문을 제어하여 접근 허용

### 기술 스택

- **프론트엔드**: Java, Android Studio
- **백엔드**: Java, Spring Boot, REST API
- **데이터베이스**: MySQL
- **IoT 장치**: 아두이노, 바코드 스캐너, 출입문 제어기
- **버전 관리**: Git, GitHub


### 설치 및 실행 방법

#### 1. 백엔드 서버 설치 및 실행
1. **Spring Boot 프로젝트 클론**:
    ```bash
    git clone https://github.com/your-repo/happy_server.git
    cd happy_server
    ```

2. **필요한 의존성 설치**:
    ```bash
    ./mvnw install
    ```

3. **애플리케이션 실행**:
    ```bash
    ./mvnw spring-boot:run
    ```

4. **애플리케이션 설정 (application.properties)**:
    - 데이터베이스 설정 등 필요한 환경 설정을 수정합니다.

#### 2. 안드로이드 앱 설치 및 실행
1. **Android 프로젝트 클론**:
    ```bash
    git clone https://github.com/your-repo/happy_app.git
    cd happy_app
    ```

2. **Android Studio에서 프로젝트 열기**:
    - Android Studio에서 프로젝트를 열고 필요한 SDK 및 라이브러리를 설치합니다.

3. **앱 빌드 및 실행**:
    - Android 기기나 에뮬레이터에서 앱을 빌드하고 실행합니다.

#### 3. IoT 장치 설정
1. **아두이노 설정**:
    - 아두이노에 필요한 라이브러리를 설정합니다.
    - 바코드 스캐너와 출입문 제어기를 Raspberry Pi에 연결합니다.

2. **스크립트 실행**:
    - 바코드 스캐너로 바코드를 스캔하고 출입문을 제어하는 스크립트를 실행합니다.