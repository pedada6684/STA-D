import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String title;
  final List<Widget>? actions;
  final TextStyle? titleStyle;
  final bool showBackButton;
  final TabController? tabController;

  const CustomAppBar({
    super.key,
    required this.title,
    this.actions,
    this.titleStyle,
    this.showBackButton = false,
    this.tabController,
  });

  @override
  Widget build(BuildContext context) {
    return AppBar(
      leading: showBackButton
          ? IconButton(
              icon: Icon(Icons.arrow_back_ios_new_rounded),
              color: mainWhite,
              onPressed: () => Navigator.pop(context),
            )
          : null,
      centerTitle: true,
      title: Text(title, style: titleStyle ?? TextStyle()),
      actions: actions,
      backgroundColor: mainNavy,
      bottom: tabController != null
          ? PreferredSize(
              preferredSize: Size.fromHeight(48.0),
              child: Material(
                color: mainWhite,
                child: TabBar(
                  controller: tabController,
                  indicator: BoxDecoration(
                    border: Border(
                      bottom: BorderSide(color: mainNavy, width: 3.0),
                    ),
                  ),
                  indicatorPadding: EdgeInsets.symmetric(horizontal: 32.0),
                  indicatorSize: TabBarIndicatorSize.tab,
                  labelStyle: TextStyle(
                    color: mainNavy,
                    fontSize: 16.0,
                  ),
                  unselectedLabelStyle: TextStyle(
                    color: mainNavy,
                    fontSize: 16.0,
                  ),
                  tabs: const [
                    Tab(text: '상품상세'),
                    Tab(text: '후기'),
                  ],
                ),
              ),
            )
          : null,
    );
  }

  @override
  Size get preferredSize =>
      Size.fromHeight(kToolbarHeight + (tabController != null ? 48.0 : 0.0));
}
