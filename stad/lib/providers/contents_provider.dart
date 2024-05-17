import 'package:flutter/material.dart';
import 'package:stad/models/contents_model.dart';
import 'package:stad/services/contents_service.dart';

class ContentProvider with ChangeNotifier {
  Content? _featuredContent;
  List<Map<String, dynamic>> _popularContents = [];
  List<Map<String, dynamic>> _adverts = [];

  Content? get featuredContent => _featuredContent;
  List<Map<String, dynamic>> get adverts => _adverts;

  List<Map<String, dynamic>> get popularContents => _popularContents;

  void setFeaturedContent(Content content) {
    _featuredContent = content;
    notifyListeners();
  }

  void setAdverts(List<Map<String, dynamic>> adverts) {
    _adverts = adverts;
    notifyListeners();
  }

  void setPopularContents(List<Map<String, dynamic>> contents) {
    _popularContents = contents;
    notifyListeners();
  }

  Future<void> fetchFeaturedContent(int contentId) async {
    try {
      ContentsService contentsService = ContentsService();
      var content = await contentsService.fetchContentDetails(contentId);
      setFeaturedContent(content);
    } catch (e) {
      print('Failed to fetch featured content: $e');
    }
  }

  Future<void> fetchPopularContent() async {
    try {
      ContentsService contentsService = ContentsService();
      var contents = await contentsService.fetchPopularContent();
      setPopularContents(contents);
    } catch (e) {
      print('Failed to fetch popular content: $e');
    }
  }

  Future<int?> fetchCurrentOrPopularContent(int userId) async {
    try {
      ContentsService contentsService = ContentsService();
      var contentDetailId = await contentsService.getCurrViewContent(userId);
      if (contentDetailId != null) {
        await fetchFeaturedContent(contentDetailId);
        return contentDetailId;
      } else {
        await fetchPopularContent();
        return null;
      }
    } catch (e) {
      print('Failed to fetch current or popular content: $e');
      await fetchPopularContent();
      return null;
    }
  }
}
