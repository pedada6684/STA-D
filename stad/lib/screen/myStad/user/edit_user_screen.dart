import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:image_picker/image_picker.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/providers/user_provider.dart';
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

    print("이건 이미지야"+imagePath.toString());

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
            CustomTextFormField(
              labelText: '이름',
              initialValue: user?.name,
              readOnly: true,
            ),
            CustomTextFormField(
              labelText: '닉네임',
              controller: _nicknameController,
            ),
            CustomTextFormField(
              labelText: '전화번호',
              controller: _phoneController,
              keyboardType: TextInputType.number,
              inputFormatters: <TextInputFormatter>[
                FilteringTextInputFormatter.digitsOnly,
                LengthLimitingTextInputFormatter(10), // 글자수 제한
              ],
            ),
            CustomTextFormField(
              labelText: '이메일',
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

class CustomTextFormField extends StatelessWidget {
  final String labelText;
  final bool readOnly;
  final TextEditingController? controller;
  final TextInputType? keyboardType;
  final List<TextInputFormatter>? inputFormatters;
  final String? initialValue;

  const CustomTextFormField({
    Key? key,
    required this.labelText,
    this.readOnly = false,
    this.controller,
    this.keyboardType,
    this.inputFormatters,
    this.initialValue,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return TextFormField(
      controller: controller,
      initialValue: controller == null ? initialValue : null,
      readOnly: readOnly,
      decoration: InputDecoration(
          labelText: labelText,
          labelStyle: TextStyle(color: midGray),
          floatingLabelBehavior: FloatingLabelBehavior.always,
          enabledBorder: UnderlineInputBorder(
            borderSide: BorderSide(color: midGray),
          ),
          focusedBorder: UnderlineInputBorder(
              borderSide: BorderSide(color: mainNavy, width: 2))),
      keyboardType: keyboardType,
      inputFormatters: inputFormatters,
      cursorColor: mainNavy,
      style: TextStyle(color: mainBlack),
    );
  }
}
