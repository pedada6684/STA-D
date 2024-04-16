import 'package:flutter/material.dart';
import 'package:stad/component/app_bar.dart';
import 'package:stad/component/button.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/screen/login/login_screen.dart';
import 'package:stad/screen/myStad/mycommercial_screen.dart';
import 'package:stad/screen/myStad/mycontents_screen.dart';
import 'package:stad/screen/myStad/qr_screen.dart';

class MyStadScreen extends StatefulWidget {
  const MyStadScreen({super.key});

  @override
  State<MyStadScreen> createState() => _MyStadScreenState();
}

class _MyStadScreenState extends State<MyStadScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: backGray,
      appBar: CustomAppBar(
        title: '마이스테디',
        titleStyle: TextStyle(
            color: mainWhite, fontWeight: FontWeight.bold, fontSize: 18.0),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            buildContainer(),
            _buildHeadListTile(title: '주문 내역'),
            _buildHeadListTile(title: '배송지 관리'),
            _buildHeadListTile(title: '상품 리뷰'),
            SizedBox(
              height: 10.0,
            ),
            _buildHeadListTile(
              title: '내가 본 광고',
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => MyCommercialScreen()),
                );
              },
            ),
            _buildHeadListTile(
              title: '내가 본 콘텐츠',
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => MyContentsScreen()),
                );
              },
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (context) => LoginScreen()));
              },
              child: Text('로그인'),
            )
          ],
        ),
      ),
    );
  }

  Widget _buildHeadListTile({required String title, VoidCallback? onTap}) {
    return ListTile(
      tileColor: mainWhite,
      title: Padding(
        padding: const EdgeInsets.symmetric(vertical: 12.0, horizontal: 8.0),
        child: Text(
          title,
          style: TextStyle(fontSize: 16.0, color: mainBlack),
        ),
      ),
      trailing: Icon(
        Icons.chevron_right,
        color: darkGray,
      ),
      onTap: onTap,
    );
  }
}

class buildContainer extends StatelessWidget {
  const buildContainer({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 230,
      width: MediaQuery.of(context).size.width,
      color: mainNavy,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Align(
            alignment: Alignment.topRight,
            child: IconButton(
              onPressed: () {},
              icon: Icon(
                Icons.settings_rounded,
                color: mainWhite,
                size: 32.0,
              ),
            ),
          ),
          Column(
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: <Widget>[
                  CircleAvatar(
                    backgroundColor: mainWhite,
                    radius: 48.0,
                    child: Icon(
                      Icons.person,
                      color: mainNavy,
                      size: 48.0,
                    ),
                  ),
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      Text(
                        '박지운',
                        style: TextStyle(
                            color: mainWhite,
                            fontSize: 24.0,
                            fontWeight: FontWeight.bold),
                      ),
                      SizedBox(
                        height: 4.0,
                      ),
                      Text(
                        'zizi@naver.com',
                        style: TextStyle(color: mainWhite, fontSize: 16.0),
                      ),
                      SizedBox(
                        height: 4.0,
                      ),
                      Text(
                        '010-1004-1004',
                        style: TextStyle(color: mainWhite, fontSize: 16.0),
                      ),
                    ],
                  ),
                ],
              ),
              SizedBox(
                height: 16.0,
              ),
              CustomElevatedButton(
                text: '새 기기 연결하기',
                onPressed: () {
                  _navigateToQRScreen(context);
                },
                textColor: mainNavy,
                backgroundColor: mainWhite,
              )
            ],
          ),
        ],
      ),
    );
  }

  void _navigateToQRScreen(BuildContext context) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => QRScreen(),
      ),
    );
  }
}
