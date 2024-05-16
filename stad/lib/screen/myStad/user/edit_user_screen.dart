import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:image_picker/image_picker.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/user_model.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/screen/myStad/myStad_screen.dart';
import 'package:stad/services/user_service.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/button.dart';

class EditUserScreen extends StatefulWidget {
  const EditUserScreen({super.key});

  @override
  State<EditUserScreen> createState() => _EditUserScreenState();
}

class _EditUserScreenState extends State<EditUserScreen> {
  final TextEditingController _phoneController = TextEditingController();
  final TextEditingController _nicknameController = TextEditingController();
  final ImagePicker _picker = ImagePicker();
  XFile? _imageFile;

  @override
  void initState() {
    super.initState();
    final user = Provider.of<UserProvider>(context, listen: false).user;
    _phoneController.text = user?.phone ?? '';
    _nicknameController.text = user?.nickname ?? '';
  }

  @override
  void dispose() {
    _phoneController.dispose();
    _nicknameController.dispose();
    super.dispose();
  }

  Future<void> _updateProfile() async {
    // 이미지 파일이 선택되지 않았다면 기본 이미지 경로나 null을 사용
    String? imagePath = _imageFile?.path;

    bool success = await Provider.of<UserService>(context, listen: false)
        .updateUserProfile(context, _nicknameController.text,
            _phoneController.text, imagePath);

    if (success) {
      showDialog(
        context: context,
        builder: (context) => Padding(
          padding: const EdgeInsets.all(8.0),
          child: AlertDialog(
            backgroundColor: mainWhite,
            surfaceTintColor: mainWhite,
            title: Text(
              '성공',
              style: TextStyle(
                  color: mainNavy, fontSize: 14.0, fontWeight: FontWeight.bold),
            ),
            content: Text(
              '사용자 정보가 업데이트 되었습니다.',
              style: TextStyle(color: mainNavy, fontSize: 16.0),
            ),
            actions: <Widget>[
              TextButton(
                style: ButtonStyle(
                  backgroundColor: MaterialStatePropertyAll(mainNavy),
                  shape: MaterialStatePropertyAll(
                    RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                  ),
                ),
                child: Text(
                  '확인',
                  style: TextStyle(color: mainWhite),
                ),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          ),
        ),
      );
    } else {
      print('에러');
    }
  }

  Future<void> _pickImage() async {
    final XFile? pickedFile =
        await _picker.pickImage(source: ImageSource.gallery);
    if (pickedFile != null) {
      setState(() {
        _imageFile = pickedFile;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    final user = Provider.of<UserProvider>(context).user;

    return Scaffold(
      backgroundColor: mainWhite,
      appBar: CustomAppBar(
        title: '정보 수정',
        showBackButton: true,
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: ListView(
          children: [
            GestureDetector(
              onTap: _pickImage,
              child: CircleAvatar(
                backgroundImage: _imageFile != null
                    ? FileImage(File(_imageFile!.path))
                    : (user?.profilePicture != null
                        ? NetworkImage(user!.profilePicture!)
                        : AssetImage('path/to/default_profile_image.png')
                            as ImageProvider),
                radius: 60,
              ),
            ),
            TextFormField(
              initialValue: user?.name,
              readOnly: true,
              decoration: InputDecoration(labelText: '이름'),
            ),
            TextFormField(
              controller: _nicknameController,
              readOnly: false,
              decoration: InputDecoration(labelText: '닉네임'),
            ),
            TextFormField(
              controller: _phoneController,
              decoration: InputDecoration(labelText: '전화번호'),
              keyboardType: TextInputType.number, // 키보드 타입을 숫자로 설정
              inputFormatters: <TextInputFormatter>[
                FilteringTextInputFormatter.digitsOnly, // 숫자만 입력 허용
                LengthLimitingTextInputFormatter(10), // 글자수 제한
              ],
            ),
            // 기타 정보들을 읽기 전용 필드로 표시
            TextFormField(
              initialValue: user?.email,
              readOnly: true,
              decoration: InputDecoration(labelText: '이메일'),
            ),
          ],
        ),
      ),
      bottomNavigationBar: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: CustomElevatedButton(
            text: '정보 수정하기',
            textColor: mainWhite,
            backgroundColor: mainNavy,
            onPressed: _updateProfile,
          ),
        ),
      ),
    );
  }
}
