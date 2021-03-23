CKEDITOR.editorConfig = function(config) {
	config.resize_enabled = false;
	config.toolbar = 'Complex';
	config.toolbar_Simple = [ [ 'Bold', 'Italic', '-', 'TextColor', '-',
			'NumberedList', 'BulletedList', '-', 'Link', 'Unlink', '-' ] ];
	config.toolbar_Complex = [
			[ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript',
					'Superscript', 'TextColor', 'BGColor', '-', 'Cut', 'Copy',
					'Paste', 'Link', 'Unlink', 'Image' ],
			[ 'Undo', 'Redo', '-', 'JustifyLeft', 'JustifyCenter',
					'JustifyRight', 'JustifyBlock', 'NumberedList',
					'BulletedList' ],
			[ 'Table', 'Smiley', 'SpecialChar', 'PageBreak', 'Styles',
					'Format', 'Font', 'FontSize', 'Maximize' ] ];
	config.removeButtons = 'Save,NewPage,ExportPdf,Preview,Print,Image,Flash';
};