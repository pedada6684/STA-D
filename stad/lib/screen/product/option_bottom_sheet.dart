import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/cart_model.dart';
import 'package:stad/models/product_model.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/screen/order/order_screen.dart';
import 'package:stad/services/cart_service.dart';
import 'package:stad/widget/custom_dropdown.dart';
import 'package:stad/widget/quantity_changer.dart';

void showProductOptionBottomSheet(
    BuildContext context,
    ProductInfo? productInfo,
    List<ProductType> productTypes,
    String title,
    int advertId,
    int contentId,
    VoidCallback onClose) {
  showModalBottomSheet(
    isScrollControlled: true,
    isDismissible: true,
    context: context,
    builder: (BuildContext context) {
      return SingleChildScrollView(
        child: ConstrainedBox(
          constraints: BoxConstraints(
            minHeight: MediaQuery.of(context).size.height * 0.5,
          ),
          child: IntrinsicHeight(
            child: ProductOptionBottomSheet(
              productInfo: productInfo,
              productTypes: productTypes,
              title: title,
              advertId: advertId,
              contentId: contentId,
              onClose: onClose,
            ),
          ),
        ),
      );
    },
  );
}

class ProductOptionBottomSheet extends StatefulWidget {
  final ProductInfo? productInfo;
  final List<ProductType> productTypes;
  final String title;
  final int advertId;
  final int contentId;
  final VoidCallback onClose;

  const ProductOptionBottomSheet({
    Key? key,
    this.productInfo,
    required this.productTypes,
    required this.title,
    required this.advertId,
    required this.contentId,
    required this.onClose,
  }) : super(key: key);

  @override
  _ProductOptionBottomSheetState createState() =>
      _ProductOptionBottomSheetState();
}

class _ProductOptionBottomSheetState extends State<ProductOptionBottomSheet> {
  String? selectedProductOption;
  String? selectedOption;
  bool isProductExpanded = false;
  bool isOptionExpanded = false;
  int? selectedProductIndex;
  int? selectedOptionIndex;
  List<ProductType> selectedProducts = [];
  Map<int, int> quantities = {};
  List<int> optionIds = [];
  final CartService _cartService = CartService();

  void addToCart() async {
    if (selectedProducts.isNotEmpty) {
      List<CartProductDetail> products = selectedProducts.map((product) {
        return CartProductDetail(
          productTypeId: product.id,
          quantity: quantities[product.id] ?? 0,
          advertId: widget.advertId,
          contentId: widget.contentId,
          optionId: optionIds.isNotEmpty ? optionIds[0] : -1,
        );
      }).toList();

      int userId =
          Provider.of<UserProvider>(context, listen: false).userId ?? 0;
      await _cartService.addProductToCart(context, userId, products);
      widget.onClose();
      showCustomSnackbar(context, '상품이 추가되었습니다.');
    }
  }

  void showCustomSnackbar(BuildContext context, String message) {
    final snackbar = SnackBar(
      content: Container(
        padding: EdgeInsets.symmetric(horizontal: 24.0),
        width: MediaQuery.of(context).size.width * 0.6,
        child: Text(
          message,
          textAlign: TextAlign.center,
        ),
      ),
      behavior: SnackBarBehavior.floating,
      backgroundColor: mainBlack.withOpacity(0.7),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(10),
      ),
      duration: Duration(seconds: 2),
    );

    ScaffoldMessenger.of(context)
      ..hideCurrentSnackBar()
      ..showSnackBar(snackbar);
  }

  void addProduct(ProductType product, int optionId) {
    for (var i = 0; i < selectedProducts.length; i++) {
      if (selectedProducts[i].id == product.id && optionIds[i] == optionId) {
        setState(() {
          quantities[product.id] = (quantities[product.id] ?? 0) + 1;
        });
        return;
      }
    }
    setState(() {
      quantities[product.id] = 1;
      selectedProducts.add(product);
      optionIds.add(optionId);
    });
  }

  void selectProductOption(String? value) {
    if (value != null) {
      int index = widget.productTypes.indexWhere((p) => p.name == value);
      int optionId = -1;
      if (!widget.productTypes[index].productOptions.isEmpty) {
        optionId = widget.productTypes[index].productOptions.first.value;
      }
      setState(() {
        selectedProductIndex = index;
        selectedOptionIndex = null;
        isProductExpanded = false;
        addProduct(widget.productTypes[index], optionId);
        if (widget.productTypes[index].productOptions.isEmpty) {
          isOptionExpanded = false;
        }
      });
    }
  }

  void selectOption(String? option) {
    if (option != null && selectedProductIndex != null) {
      int optionIndex = widget
          .productTypes[selectedProductIndex!].productOptions
          .indexWhere((o) => o.name == option);
      int optionId = widget.productTypes[selectedProductIndex!]
          .productOptions[optionIndex].value;
      setState(() {
        selectedOption = option;
        selectedOptionIndex = optionIndex;
        addProduct(widget.productTypes[selectedProductIndex!], optionId);
        isOptionExpanded = false;
      });
    }
  }

  void _navigateToOrderScreen() {
    if (selectedProducts.isNotEmpty) {
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => OrderScreen(
            productInfo: widget.productInfo,
            productTypes: selectedProducts,
            quantities: quantities,
            deliveryFee: 2500,
            title: widget.title,
            optionIds: optionIds,
            advertId: widget.advertId,
            contentId: widget.contentId,
          ),
        ),
      );
    }
  }

  Widget _buildActionButtons(BuildContext context) {
    bool isCartButtonDisabled = selectedProducts.isEmpty;

    return Padding(
      padding: const EdgeInsets.only(top: 20.0),
      child: Row(
        children: [
          Expanded(
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                foregroundColor: mainNavy,
                textStyle: TextStyle(fontSize: 16),
                side: isCartButtonDisabled
                    ? null
                    : BorderSide(color: mainNavy, width: 1),
                surfaceTintColor: mainWhite,
                backgroundColor: isCartButtonDisabled ? mainGray : mainWhite,
                padding: EdgeInsets.symmetric(vertical: 14),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.all(Radius.circular(10.0)),
                ),
              ),
              onPressed: isCartButtonDisabled ? null : addToCart,
              child: Text('장바구니 담기'),
            ),
          ),
          SizedBox(width: 10),
          Expanded(
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                foregroundColor: mainWhite,
                textStyle: TextStyle(fontSize: 16),
                surfaceTintColor: mainNavy,
                backgroundColor: isCartButtonDisabled ? mainGray : mainNavy,
                padding: EdgeInsets.symmetric(vertical: 15.8),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.all(Radius.circular(10.0)),
                ),
              ),
              onPressed: isCartButtonDisabled ? null : _navigateToOrderScreen,
              child: Text('구매하기'),
            ),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    List<String> productOptions =
    widget.productTypes.map((p) => p.name).toList();
    List<String>? currentOptions = selectedProductIndex != null
        ? widget.productTypes[selectedProductIndex!].productOptions
        .map((o) => o.name)
        .toList()
        : ["상품을 선택해주세요"];

    return Container(
      decoration: BoxDecoration(
        color: mainWhite,
        borderRadius: BorderRadius.only(
          topLeft: Radius.circular(25.0),
          topRight: Radius.circular(25.0),
        ),
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 16.0),
        child: Column(
          mainAxisSize: MainAxisSize.min,
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
            SizedBox(height: 40.0),
            CustomDropdown(
              title: '상품선택',
              options: productOptions,
              isExpanded: isProductExpanded,
              selectedOption: selectedProductIndex != null
                  ? productOptions[selectedProductIndex!]
                  : null,
              onToggle: () {
                if (isOptionExpanded) {
                  setState(() {
                    isOptionExpanded = false;
                  });
                }
                setState(() => isProductExpanded = !isProductExpanded);
              },
              onSelect: selectProductOption,
            ),
            SizedBox(height: 15),
            if (selectedProductIndex != null &&
                !widget.productTypes[selectedProductIndex!]
                    .productOptions.isEmpty) ...[
              CustomDropdown(
                title: '옵션선택',
                options: currentOptions,
                isExpanded: isOptionExpanded,
                selectedOption: selectedOptionIndex != null
                    ? currentOptions[selectedOptionIndex!]
                    : null,
                onToggle: () {
                  if (isProductExpanded) {
                    setState(() {
                      isProductExpanded = false;
                    });
                  }
                  setState(() => isOptionExpanded = !isOptionExpanded);
                },
                onSelect: selectOption,
              ),
            ],
            if (selectedProductIndex != null &&
                (selectedOptionIndex != null ||
                    widget.productTypes[selectedProductIndex!]
                        .productOptions.isEmpty)) ...[
              ...selectedProducts.map((product) {
                return Column(
                  children: [
                    ProductDetails(
                      productType: product,
                      onCancel: () {
                        setState(() {
                          selectedProducts.remove(product);
                          quantities.remove(product.id);
                          if (selectedProducts.isEmpty) {
                            selectedProductIndex = null;
                          }
                        });
                      },
                    ),
                    Padding(
                      padding: const EdgeInsets.only(bottom: 8.0),
                      child: QuantityChanger(
                        initialQuantity: quantities[product.id] ?? 1,
                        maxQuantity: product.quantity,
                        onQuantityChanged: (newQuantity) {
                          setState(() {
                            quantities[product.id] = newQuantity;
                          });
                        },
                      ),
                    ),
                  ],
                );
              }).toList(),
            ],
            _buildActionButtons(context),
          ],
        ),
      ),
    );
  }
}

class ProductDetails extends StatelessWidget {
  final ProductType productType;
  final VoidCallback onCancel;

  const ProductDetails({
    Key? key,
    required this.productType,
    required this.onCancel,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Expanded(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 8.0, vertical: 4.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(productType.name,
                    style:
                    TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
                Text("${productType.price}원", style: TextStyle(color: midGray)),
                Text("재고: ${productType.quantity}",
                    style: TextStyle(color: midGray)),
              ],
            ),
          ),
        ),
        IconButton(
          iconSize: 20.0,
          onPressed: onCancel,
          icon: Icon(
            Icons.cancel_rounded,
            color: mainGray,
          ),
        ),
      ],
    );
  }
}
