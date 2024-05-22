import cv2
import sys
import random
import numpy as np
import pyzbar.pyzbar as pyzbar
import requests

def main():
    try:
        cap = cv2.VideoCapture("C:/Users/hyebin/Desktop/capstone/test_qr.mp4")
        if not cap.isOpened():
            print("비디오 파일을 열 수 없습니다. 파일 경로를 확인해 주세요.")
            return

        while cap.isOpened():
            ret, frame = cap.read()
            if ret:
                frame, barcode_data_list = read_frame(frame)
                cv2.imshow("barcode reader", frame)
                if barcode_data_list:
                    for barcode_data in barcode_data_list:
                        send_barcode_to_server(barcode_data)
                if cv2.waitKey(1) == 27:
                    break
            else:
                print("비디오 스트림의 끝에 도달했거나 프레임을 읽는 데 실패했습니다.")
                break
    except Exception as e:
        print(f"Error occurred: {type(e).__name__}, {e}")
    finally:
        cap.release()
        cv2.destroyAllWindows()

font = cv2.FONT_HERSHEY_SIMPLEX

# 바코드 인식 및 테두리 설정
def read_frame(frame):
    barcode_data_list = []
    try:
        barcodes = pyzbar.decode(frame)
        for barcode in barcodes:
            x, y, w, h = barcode.rect
            barcode_info = barcode.data.decode('utf-8')
            cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 0, 255), 2)
            cv2.putText(frame, barcode_info, (x, y - 20), font, 0.5, (0, 0, 255), 1)
            barcode_data_list.append(barcode_info)

        return frame, barcode_data_list
    except Exception as e:
        print(f"Error occurred while decoding barcodes: {type(e).__name__}, {e}")
        return frame, []

def send_barcode_to_server(barcode_data):
    url = 'http://localhost:8080/barcode'  # 서버의 URL 주소
    try:
        response = requests.post(url, json={"barcode": barcode_data})
        print("Server response:", response.text)
    except requests.exceptions.RequestException as e:
        print(f"Failed to send barcode to server: {e}")


if __name__ == "__main__":
    main()
