//api 받아오기
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/widgets.dart';
import 'package:remedi_kopo/remedi_kopo.dart';
import 'package:stad/component/button.dart';
import 'package:stad/constant/colors.dart';

class AddressScreen extends StatefulWidget {
  const AddressScreen({super.key});

  @override
  State<AddressScreen> createState() => _AddressScreenState();
}

class _AddressScreenState extends State<AddressScreen> {
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _adnickController = TextEditingController();
  final TextEditingController _phoneController = TextEditingController();
  final TextEditingController _postcodeController = TextEditingController();
  final TextEditingController _addressController = TextEditingController();
  final TextEditingController _addressDetailController =
      TextEditingController();
  bool isDefaultAddress = false;

  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  Map<String, String> formData = {};

  Widget _gap() {
    return const SizedBox(
      height: 15,
    );
  }

  void _searchAddress(BuildContext context) async {
    KopoModel? model = await Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => RemediKopo(),
      ),
    );

    if (model != null) {
      _postcodeController.text = model.zonecode ?? '';
      formData['postcode'] = model.zonecode ?? '';

      _addressController.text = model.address ?? '';
      formData['address'] = model.address ?? '';

      _addressDetailController.text = model.buildingName ?? '';
      formData['address_detail'] = model.buildingName ?? '';
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          color: mainWhite,
          borderRadius: BorderRadius.only(
              topRight: Radius.circular(20.0), topLeft: Radius.circular(20.0))),
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 30.0, vertical: 16),
        child: Column(
          children: <Widget>[
            Container(
              width: 40,
              height: 4,
              margin: const EdgeInsets.only(bottom: 8.0),
              decoration: BoxDecoration(
                color: darkGray,
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            _gap(),
            Text(
              '배송지 입력',
              style: TextStyle(fontSize: 18.0),
            ),
            SizedBox(
              height: 20,
            ),
            _buildTextField(_nameController, '이름'),
            _gap(),
            _buildTextField(_adnickController, '배송지명'),
            _gap(),
            _buildTextField(_phoneController, '핸드폰 번호'),
            _gap(),
            _buildPostalCodeField(),
            _gap(),
            _buildTextField(_addressController, '기본주소', readOnly: true),
            _gap(),
            _buildTextField(_addressDetailController, '상세주소'),
            _buildCustomCheckbox(),
            CustomElevatedButton(
                text: '완료', textColor: mainWhite, backgroundColor: mainNavy)
          ],
        ),
      ),
    );
  }

  Widget _buildCustomCheckbox() {
    return InkWell(
      onTap: () {
        setState(() {
          isDefaultAddress = !isDefaultAddress;
        });
      },
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 15.0),
        child: Container(
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: <Widget>[
              Icon(
                isDefaultAddress
                    ? Icons.check_circle
                    : Icons.check_circle_outline,
                color: isDefaultAddress ? mainNavy : midGray,
                size: 32.0,
              ),
              SizedBox(width: 8.0),
              Text(
                '기본 배송지로 설정',
                style: TextStyle(
                  color: mainBlack,
                  fontSize: 16.0
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildPostalCodeField() {
    return TextFormField(
      controller: _postcodeController,
      readOnly: true,
      style: TextStyle(color: mainBlack),
      decoration: InputDecoration(
        labelText: '우편번호',
        labelStyle: TextStyle(color: midGray),
        enabledBorder: OutlineInputBorder(
          borderSide: BorderSide(color: mainNavy),
          borderRadius: BorderRadius.all(Radius.circular(5)),
        ),
        focusedBorder: OutlineInputBorder(
          borderSide: BorderSide(color: mainNavy),
        ),
        suffixIcon: TextButton(
          style: TextButton.styleFrom(
            padding: EdgeInsets.symmetric(horizontal: 16.0),
            minimumSize: Size(40, 40),
          ),
          onPressed: () => _searchAddress(context), // 메서드 호출 방식 변경
          child: Text(
            '우편번호검색',
            style: TextStyle(color: midGray, fontSize: 14),
          ),
        ),
      ),
    );
  }
}

Widget _buildTextField(
  TextEditingController controller,
  String placeholder, {
  bool readOnly = false,
  bool isFixedLabel = false,
}) {
  return TextFormField(
    controller: controller,
    readOnly: readOnly,
    style: TextStyle(color: mainBlack),
    decoration: InputDecoration(
      labelText: placeholder,
      labelStyle: TextStyle(color: midGray),
      floatingLabelBehavior: isFixedLabel ? FloatingLabelBehavior.always : null,
      enabledBorder: OutlineInputBorder(
          borderSide: BorderSide(color: mainNavy),
          borderRadius: BorderRadius.all(Radius.circular(5))),
      focusedBorder: OutlineInputBorder(
        borderSide: BorderSide(color: mainNavy),
      ),
    ),
  );
}
