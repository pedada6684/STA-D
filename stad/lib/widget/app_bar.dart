import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String title;
  final List<Widget>? actions;
  final TextStyle? titleStyle;
  final bool showBackButton;
  final TabController? tabController;
  final bool isLoading; // 로딩 상태 추가
  final double progressValue;

  const CustomAppBar({
    super.key,
    required this.title,
    this.actions,
    this.titleStyle,
    this.showBackButton = false,
    this.tabController,
    this.isLoading = false,
    this.progressValue = 0.0, // 초기값 false
  });

  @override
  Widget build(BuildContext context) {
    return AppBar(
      scrolledUnderElevation: 0,
      shape: Border(bottom: BorderSide(color: mainGray)),
      leading: showBackButton
          ? IconButton(
              icon: Icon(Icons.arrow_back_ios_new_rounded),
              color: mainNavy,
              onPressed: () => Navigator.pop(context),
            )
          : null,
      centerTitle: true,
      title: Text(title,
          style: titleStyle ??
              TextStyle(
                  color: mainNavy,
                  fontWeight: FontWeight.bold,
                  fontSize: 18.0)),
      actions: actions,
      backgroundColor: mainWhite,
      bottom: isLoading
          ? PreferredSize(
              preferredSize: Size.fromHeight(4.0),
              child: LinearProgressIndicator(
                value: progressValue,
                backgroundColor: mainWhite,
                valueColor: AlwaysStoppedAnimation<Color>(mainNavy),
              ),
            )
          : tabController != null
              ? PreferredSize(
                  preferredSize: Size.fromHeight(48.0),
                  child: Material(
                    color: mainWhite,
                    child: TabBar(
                      controller: tabController,
                      indicator: BoxDecoration(
                        border: Border(
                          bottom: BorderSide(color: mainNavy, width: 5.0),
                        ),
                      ),
                      indicatorPadding: EdgeInsets.symmetric(horizontal: 32.0),
                      indicatorSize: TabBarIndicatorSize.tab,
                      indicatorColor: mainNavy,
                      indicatorWeight: 2,
                      labelStyle: TextStyle(
                        color: mainNavy,
                        fontSize: 14.0,
                      ),
                      unselectedLabelStyle: TextStyle(
                        color: mainNavy,
                        fontSize: 14.0,
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
  Size get preferredSize => Size.fromHeight(kToolbarHeight +
      (tabController != null ? 48.0 : 0.0) +
      (isLoading ? 4.0 : 0.0));
}
