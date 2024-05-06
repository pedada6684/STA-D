import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:go_router/go_router.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/main.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/screen/home/home_screen.dart';
import 'package:stad/screen/login/login_screen.dart';
import 'package:stad/screen/myStad/qr_screen.dart';
import 'package:stad/screen/myStad/shop/myaddress_screen.dart';
import 'package:stad/screen/myStad/shop/myorder_scren.dart';
import 'package:stad/screen/myStad/shop/myreview_screen.dart';
import 'package:stad/screen/myStad/stad/mycommercial_screen.dart';
import 'package:stad/screen/myStad/user/edit_user_screen.dart';
import 'package:stad/services/user_service.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/button.dart';

import 'stad/mycontents_screen.dart';

class MyStadScreen extends StatefulWidget {
  const MyStadScreen({super.key});

  @override
  State<MyStadScreen> createState() => _MyStadScreenState();
}

class _MyStadScreenState extends State<MyStadScreen> {
  @override
  void initState() {
    super.initState();
    Future.microtask(
        () => Provider.of<UserProvider>(context, listen: false).fetchUser());

  }

  final GoogleSignIn _googleSignIn = GoogleSignIn();

  Future<void> _handleSignout() async {
    try {
      await _googleSignIn.signOut();
      final storage = FlutterSecureStorage();
      await storage.deleteAll();
      if (mounted) {
        GoRouter.of(context).go('/login');
      }
    } catch (error) {
      print('로그아웃 실패: $error');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainWhite,
      appBar: CustomAppBar(title: '마이스테디'),
      body: SingleChildScrollView(
        child: Column(
          children: [
            UserInfoContainer(),
            Divider(
              height: 1,
              color: mainGray,
            ),
            Container(
              color: mainWhite,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Padding(
                    padding: const EdgeInsets.symmetric(
                        vertical: 16.0, horizontal: 24.0),
                    child: Text(
                      'Shop',
                      style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                          color: darkGray),
                    ),
                  ),
                  _buildHeadListTile(
                      title: '주문 내역',
                      onTap: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => MyOrderScreen()));
                      }),
                  _buildHeadListTile(
                      title: '배송지 관리',
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => MyAddressScreen(),
                          ),
                        );
                      }),
                  _buildHeadListTile(
                      title: '상품 리뷰',
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => MyReviewScreen(),
                          ),
                        );
                      }),
                ],
              ),
            ),
            Divider(
              height: 1,
              color: mainGray,
            ),
            Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Padding(
                    padding: const EdgeInsets.symmetric(
                        vertical: 16.0, horizontal: 24.0),
                    child: Text(
                      'Stad',
                      style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                          color: darkGray),
                    ),
                  ),
                  _buildHeadListTile(
                    title: '내가 본 광고',
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => MyCommercialScreen()),
                      );
                    },
                  ),
                  _buildHeadListTile(
                    title: '내가 본 콘텐츠',
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => MyContentsScreen()),
                      );
                    },
                  ),
                ],
              ),
            ),
            Divider(
              height: 1,
              color: mainGray,
            ),
            _buildHeadListTile(
              title: '로그아웃',
              onTap: _handleSignout,
            ),
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
          style: TextStyle(fontSize: 14.0, color: mainBlack),
        ),
      ),
      trailing: Icon(
        Icons.chevron_right,
        color: darkGray,
        size: 30.0,
      ),
      onTap: onTap,
    );
  }
}

class UserInfoContainer extends StatelessWidget {
  const UserInfoContainer({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Consumer<UserProvider>(
      builder: (context, userProvider, child) {
        final userModel = userProvider.user;
        return Container(
          height: 230,
          width: MediaQuery.of(context).size.width,
          color: mainWhite,
          child: userModel != null
              ? Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    // Align(
                    //   alignment: Alignment.topRight,
                    //   child: TextButton(
                    //     onPressed: () {},
                    //     child: Text(
                    //       '내 정보 수정하기',
                    //       style: TextStyle(color: mainNavy),
                    //     ),
                    //   ),
                    // ),
                    Padding(
                      padding: const EdgeInsets.symmetric(
                          horizontal: 20.0, vertical: 4.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              CircleAvatar(
                                backgroundColor: mainNavy,
                                radius: 40.0,
                                backgroundImage: userModel.profilePicture !=
                                        null
                                    ? NetworkImage(userModel.profilePicture!)
                                    : AssetImage(
                                            'assets/image/default_profile.png')
                                        as ImageProvider,
                              ),
                              SizedBox(
                                width: 16.0,
                              ),
                              Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: <Widget>[
                                  Text(
                                    userModel.nickname ?? '닉네임을 설정해주세요.',
                                    style: TextStyle(
                                        color: mainBlack,
                                        fontSize: 16.0,
                                        fontWeight: FontWeight.bold),
                                  ),
                                  SizedBox(height: 4.0),
                                  Text(
                                    userModel.email ?? '이메일 없음',
                                    style: TextStyle(
                                        color: mainBlack, fontSize: 14.0),
                                  ),
                                  SizedBox(height: 4.0),
                                  Text(
                                    userModel.phone ?? '연락처를 추가해주세요.',
                                    style: TextStyle(
                                        color: mainBlack, fontSize: 14.0),
                                  ),
                                ],
                              ),
                            ],
                          ),
                          IconButton(
                            onPressed: () {
                              Navigator.of(context).push(MaterialPageRoute(
                                builder: (context) =>
                                    Provider<UserService>.value(
                                  value: Provider.of<UserService>(context,
                                      listen: false),
                                  child: EditUserScreen(),
                                ),
                              ));
                            },
                            icon: Icon(
                              Icons.chevron_right,
                              color: darkGray,
                              size: 30.0,
                            ),
                          ),
                        ],
                      ),
                    ),
                    SizedBox(height: 16.0),
                    CustomElevatedButton(
                      text: '새 기기 연결하기',
                      onPressed: () => _navigateToQRScreen(context),
                      textColor: mainWhite,
                      backgroundColor: mainNavy,
                    )
                  ],
                )
              : Center(
                  child: Text(
                    '로그인 정보를 불러오는 중...',
                    style: TextStyle(color: mainWhite),
                  ),
                ),
        );
      },
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
