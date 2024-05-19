import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:qr_code_scanner/qr_code_scanner.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/services/alert_service.dart';
import 'package:stad/widget/app_bar.dart';

class QRScreen extends StatefulWidget {
  const QRScreen({super.key});

  @override
  State<QRScreen> createState() => _QRScreenState();
}

class _QRScreenState extends State<QRScreen> {
  late QRViewController controller;
  final GlobalKey qrKey = GlobalKey(debugLabel: 'QR');

  void _onQRViewCreated(QRViewController controller) {
    this.controller = controller;
    controller.scannedDataStream.listen((scanData) async {
      controller.pauseCamera(); // 스캔 후 카메라 일시 정지
      print('스캔된 데이터: ${scanData.code}');

      final userId = Provider.of<UserProvider>(context, listen: false).userId;
      if (userId == null) {
        _showDialog('오류', '사용자 인증 정보를 찾을 수 없습니다.', true);
        return;
      }
      bool response =
          await AlertService().sendQrResponse(userId, scanData.code!);
      if (!response) {
        _showDialog(
          '연동 실패',
          'TV와 연동에 실패하였습니다.\n다시 시도해주세요',
          true,
        );
      } else {
        _showDialog('연동 성공', 'TV와의 연동이 완료되었습니다.', false);
      }
    });
  }

  void _showDialog(String title, String content, bool retry) {
    showDialog(
      context: context,
      barrierDismissible: false, // 대화상자 바깥을 터치해도 닫히지 않게
      builder: (BuildContext context) {
        return Dialog(
          shape:
              RoundedRectangleBorder(borderRadius: BorderRadius.circular(10.0)),
          child: Container(
            width: 300,
            height: 200,
            decoration: BoxDecoration(
              color: mainWhite,
              borderRadius: BorderRadius.circular(12.0),
            ),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Padding(
                  padding: EdgeInsets.symmetric(horizontal: 20.0),
                  child: Text(
                    content,
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: 14.0,
                      color: mainNavy,
                    ),
                  ),
                ),
                SizedBox(height: 20),
                ElevatedButton(
                  onPressed: () {
                    Navigator.of(context).pop(retry);
                  },
                  child: Text('확인',
                      style: TextStyle(fontSize: 16, color: mainWhite)),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: mainNavy,
                    fixedSize: Size(140, 50),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                  ),
                ),
              ],
            ),
          ),
        );
      },
    ).then((retry) {
      if (retry) {
        controller.resumeCamera();
      } else {
        Navigator.of(context).pop();
      }
    });
    ;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: '새 기기 연결하기',
        showBackButton: true,
      ),
      body: Column(
        children: [
          Expanded(
            flex: 5,
            child: QRView(
              key: qrKey,
              onQRViewCreated: _onQRViewCreated,
              overlay: QrScannerOverlayShape(
                borderColor: mainWhite,
                borderRadius: 10,
                borderLength: 30,
                borderWidth: 10,
                cutOutSize: 300, // 스캔 영역 크기 조절
              ),
            ),
          ),
          Expanded(
            flex: 1,
            child: Container(
              color: mainWhite,
              child: Center(
                child: Text(
                  'TV화면의 QR 코드를 스캔해주세요.',
                  style: TextStyle(color: mainNavy),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }
}
