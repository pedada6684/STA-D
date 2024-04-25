import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class ExpandableText extends StatefulWidget {
  final String text;
  final int maxLength;

  const ExpandableText({Key? key, required this.text, this.maxLength = 100})
      : super(key: key);

  @override
  _ExpandableTextState createState() => _ExpandableTextState();
}

class _ExpandableTextState extends State<ExpandableText> {
  bool _isExpanded = false;

  @override
  Widget build(BuildContext context) {
    final String displayText =
        widget.text.length > widget.maxLength && !_isExpanded
            ? widget.text.substring(0, widget.maxLength) + '...'
            : widget.text;

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          displayText,
          style: TextStyle(color: darkGray),
        ),
        widget.text.length > widget.maxLength
            ? InkWell(
                onTap: () {
                  setState(() {
                    _isExpanded = !_isExpanded;
                  });
                },
                child: Text(
                  _isExpanded ? '접기' : '더보기',
                  style:
                      TextStyle(color: mainNavy, fontWeight: FontWeight.bold),
                ),
              )
            : SizedBox(),
      ],
    );
  }
}
