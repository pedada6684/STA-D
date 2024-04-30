import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:remedi_kopo/remedi_kopo.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/services/address_service.dart';
import 'package:stad/widget/button.dart';

class AddressScreen extends StatefulWidget {
  final Function onAddressAdded;
  const AddressScreen({super.key, required this.onAddressAdded});

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
  bool isFormFilled = false;
  bool isPhoneError = false;

  // final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  Map<String, String> formData = {};

  Widget _gap() {
    return const SizedBox(
      height: 15,
    );
  }

  @override
  void initState() {
    super.initState();
    // 각각의 컨트롤러에 리스너를 추가
    _nameController.addListener(validateForm);
    _adnickController.addListener(validateForm);
    _phoneController.addListener(validateForm);
    _phoneController.addListener(() => validateNumber(_phoneController.text));
    _postcodeController.addListener(validateForm);
    _addressController.addListener(validateForm);
    _addressDetailController.addListener(validateForm);
  }

  @override
  void dispose() {
    // 컨트롤러 리스너를 제거하여 리소스 누수 방지
    _nameController.removeListener(validateForm);
    _adnickController.removeListener(validateForm);
    _phoneController.removeListener(validateForm);
    _phoneController
        .removeListener(() => validateNumber(_phoneController.text));
    _postcodeController.removeListener(validateForm);
    _addressController.removeListener(validateForm);
    _addressDetailController.removeListener(validateForm);
    super.dispose();
  }

  // 입력값이 모두 채워졌는지 검증
  void validateForm() {
    bool filled = _nameController.text.isNotEmpty &&
        _adnickController.text.isNotEmpty &&
        _phoneController.text.isNotEmpty &&
        _postcodeController.text.isNotEmpty &&
        _addressController.text.isNotEmpty &&
        _addressDetailController.text.isNotEmpty;

    setState(() {
      isFormFilled = filled;
    });
  }

  void validateNumber(String input) {
    setState(() {
      isPhoneError = !RegExp(r'^01[016789]\d{3,4}\d{4}$').hasMatch(input);
    });
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

  void addUserAddress() {
    final userProvider = Provider.of<UserProvider>(context, listen: false);
    if (userProvider.userId != null) {
      final addressService = AddressService();

      // 새로운 주소 데이터 생성
      Map<String, dynamic> newAddress = {
        'userId': userProvider.userId!,
        'name': _nameController.text,
        'phone': _phoneController.text,
        'location': _postcodeController.text + ' ' + _addressController.text,
        'locationNick': _adnickController.text,
      };

      try {
        // 주소 추가
        addressService.addAddress(newAddress).then((_) {
          widget.onAddressAdded();
          Navigator.pop(context);
        });
      } catch (e) {
        print('Error adding address: $e');
      }
    } else {
      print('로그인이 필요합니다.');
    }
  }

  Widget _buildPostalCodeField() {
    return TextFormField(
      controller: _postcodeController,
      readOnly: true,
      style: TextStyle(color: mainBlack),
      decoration: InputDecoration(
        floatingLabelBehavior: FloatingLabelBehavior.never,
        labelText: '우편번호',
        labelStyle: TextStyle(color: midGray),
        enabledBorder: UnderlineInputBorder(
          borderSide: BorderSide(color: midGray),
        ),
        focusedBorder: UnderlineInputBorder(
            borderSide: BorderSide(color: mainNavy, width: 2)),
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

  Widget _buildTextField(
      TextEditingController controller,
      String placeholder, {
        bool readOnly = false,
        bool isFixedLabel = false,
      }) {
    bool isError = placeholder == '핸드폰 번호' && isPhoneError;

    return TextFormField(
      controller: controller,
      readOnly: readOnly,
      cursorColor: mainNavy,
      style: TextStyle(color: mainBlack),
      decoration: InputDecoration(
        labelText: placeholder,
        labelStyle: TextStyle(color: midGray),
        floatingLabelBehavior:
        isFixedLabel ? FloatingLabelBehavior.always : null,
        enabledBorder: UnderlineInputBorder(
          borderSide: BorderSide(color: midGray),
        ),
        focusedBorder: UnderlineInputBorder(
            borderSide: BorderSide(color: mainNavy, width: 2)),
        errorBorder: isError
            ? UnderlineInputBorder(
          borderSide: BorderSide(color: Colors.red, width: 2),
        )
            : null,
        focusedErrorBorder: isError
            ? UnderlineInputBorder(
          borderSide: BorderSide(color: Colors.red, width: 2),
        )
            : null,
        errorText: isError ? '번호를 입력해주세요.' : null,
      ),
    );
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
              style: TextStyle(
                  fontSize: 18.0, fontWeight: FontWeight.w600, color: mainNavy),
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
            _gap(),
            _gap(),
            CustomElevatedButton(
              text: '완료',
              textColor: mainWhite,
              onPressed: isFormFilled ? addUserAddress : null,
              backgroundColor: mainNavy,
            )
          ],
        ),
      ),
    );
  }
}
