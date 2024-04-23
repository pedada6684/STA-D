import 'package:flutter/material.dart';
import 'package:qr_code_scanner/qr_code_scanner.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/widget/app_bar.dart';

class QRScreen extends StatefulWidget {
  const QRScreen({Key? key}) : super(key: key);

  @override
  State<QRScreen> createState() => _QRScreenState();
}

class _QRScreenState extends State<QRScreen> {
  late QRViewController controller;
  final GlobalKey qrKey = GlobalKey(debugLabel: 'QR');

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: '새 기기 연결하기',
        titleStyle: TextStyle(
            fontSize: 18, color: mainWhite, fontWeight: FontWeight.bold),
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
              color: mainNavy,
              child: Center(
                child: Text(
                  'QR 코드를 스캔해주세요.',
                  style: TextStyle(color: mainWhite),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  void _onQRViewCreated(QRViewController controller) {
    this.controller = controller;
    controller.scannedDataStream.listen((scanData) {
      // 스캔된 데이터 처리
      print('스캔된 데이터: ${scanData.code}');
    });
  }

  @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }
}
