import 'package:flutter/material.dart';
import 'package:stad/models/contents_model.dart';
import 'package:stad/services/contents_service.dart';

class ContentProvider with ChangeNotifier {
  Content? _featuredContent;
  List<Map<String, dynamic>> _popularContents = [];

  Content? get featuredContent => _featuredContent;

  List<Map<String, dynamic>> get popularContents => _popularContents;

  void setFeaturedContent(Content content) {
    _featuredContent = content;
    notifyListeners();
  }

  void setPopularContents(List<Map<String, dynamic>> contents) {
    _popularContents = contents;
    notifyListeners();
  }

  Future<void> fetchFeaturedContent(int contentId) async {
    ContentsService contentsService = ContentsService();
    try {
      var content = await contentsService.fetchContentDetails(contentId);
      setFeaturedContent(content);
    } catch (e) {
      print('Failed to fetch featured content: $e');
    }
  }

  Future<void> fetchPopularContent() async {
    ContentsService contentsService = ContentsService();
    try {
      var contents = await contentsService.fetchPopularContent();
      setPopularContents(contents);
    } catch (e) {
      print('Failed to fetch popular content: $e');
    }
  }
}
