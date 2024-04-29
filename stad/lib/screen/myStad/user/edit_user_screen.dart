import 'dart:io';

import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/services/user_service.dart';
import 'package:stad/widget/app_bar.dart';

class EditUserScreen extends StatefulWidget {
  const EditUserScreen({super.key});

  @override
  State<EditUserScreen> createState() => _EditUserScreenState();
}

class _EditUserScreenState extends State<EditUserScreen> {
  final TextEditingController _phoneController = TextEditingController();
  final ImagePicker _picker = ImagePicker();
  XFile? _imageFile;

  @override
  void initState() {
    super.initState();
    final user = Provider.of<UserProvider>(context, listen: false).user;
    _phoneController.text = user?.phone ?? '';
  }

  @override
  void dispose() {
    _phoneController.dispose();
    super.dispose();
  }

  Future<void> _updateProfile() async {
    bool success = await Provider.of<UserService>(context, listen: false)
        .updateUserProfile(context, _phoneController.text, _imageFile!.path);

    if (success) {
      showDialog(
        context: context,
        builder: (context) => AlertDialog(
          title: Text('성공'),
          content: Text('사용자 정보가 업데이트 되었습니다.'),
          actions: <Widget>[
            TextButton(
              child: Text('확인'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
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
                    : user?.profilePicture != null
                        ? NetworkImage(
                            user!.profilePicture!,
                          )
                        : AssetImage('path/to/default_image.png')
                            as ImageProvider,
                radius: 60,
              ),
            ),
            TextFormField(
              controller: _phoneController,
              decoration: InputDecoration(labelText: '전화번호'),
              keyboardType: TextInputType.phone,
            ),
            // 기타 정보들을 읽기 전용 필드로 표시
            TextFormField(
              initialValue: user?.email,
              readOnly: true,
              decoration: InputDecoration(labelText: '이메일'),
            ),
            ElevatedButton(
              onPressed: _updateProfile,
              child: Text('정보 수정하기'),
            ),
          ],
        ),
      ),
    );
  }
}
