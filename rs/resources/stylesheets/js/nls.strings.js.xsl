<?xml version="1.0"  encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="strings">



<!-- NLS - messages -->

var msgRequiredField = "<xsl:value-of select="@msg_required_field" />";
var msgMaxLengthExceeded = "<xsl:value-of select="@msg_max_field_length" />";
var msgInvalidDate = "<xsl:value-of select="@msg_invalid_date" />";
var msgInvalidTime = "<xsl:value-of select="@msg_invalid_time" />";
var msgInvalidNumber = "<xsl:value-of select="@msg_invalid_number" />";
var msgInvalidString = "<xsl:value-of select="@msg_invalid_string" />";
var msgInvalidNumberNoFormat = "<xsl:value-of select="@msg_invalid_number_no_format" />";
var msgProcessing = "<xsl:value-of select="@msg_processing" />";
var msgError = "<xsl:value-of select="@msg_error" />";
var msgInfo = "<xsl:value-of select="@msg_info" />";
var msgOtros = "<xsl:value-of select="@msg_others" />";
var msgOptions = "<xsl:value-of select="@msg_options" />";
var msgHideSelections = "<xsl:value-of select="@msg_hideselections" />";
var msgSelections = "<xsl:value-of select="@msg_selections" />";
var msgClose = "<xsl:value-of select="@msg_close" />";
var msgConfirmationYes = "<xsl:value-of select="@msg_confirmation_yes" />";
var msgConfirmationNo = "<xsl:value-of select="@msg_confirmation_no" />";
var msgLostData = "<xsl:value-of select="@msg_lostdata" />";
var msgAbmButtonCancel = "<xsl:value-of select="@msg_abmbuttoncancel" />";
<!-- NLS - strings -->

var descDateDField = "<xsl:value-of select="@str_day" />";
var descDateMField = "<xsl:value-of select="@str_month" />";
var descDateYField = "<xsl:value-of select="@str_year" />";
var descTimeHField = "<xsl:value-of select="@str_hour" />";
var descTimeMField = "<xsl:value-of select="@str_minute" />";
var descTimeSField = "<xsl:value-of select="@str_second" />";
var descWithNoSpaces = "<xsl:value-of select="@str_with_no_spaces" />";
var descOnlyLetterOrDigit = "<xsl:value-of select="@str_only_letter_or_digit" />";
var prefixTreeSelect = "<xsl:value-of select="@str_prefix_tree_select" />";
var prefixTreeFilterBy = "<xsl:value-of select="@str_prefix_tree_filter_by" />";
var treeNoSelectableOption = "<xsl:value-of select="@str_prefix_tree_no_selectable_option" />";

<xsl:for-each select="string">
	var global_<xsl:value-of select="@variable"/> = "<xsl:value-of select="@value" />";
</xsl:for-each>
<!-- NLS - calendar strings -->

Calendar._DN = new Array(
"<xsl:value-of select="@str_sunday" />",
"<xsl:value-of select="@str_monday" />",
"<xsl:value-of select="@str_tuesday" />",
"<xsl:value-of select="@str_wednesday" />",
"<xsl:value-of select="@str_thursday" />",
"<xsl:value-of select="@str_friday" />",
"<xsl:value-of select="@str_saturday" />",
"<xsl:value-of select="@str_sunday" />");

Calendar._MN = new Array(
"<xsl:value-of select="@str_january" />",
"<xsl:value-of select="@str_february" />",
"<xsl:value-of select="@str_march" />",
"<xsl:value-of select="@str_april" />",
"<xsl:value-of select="@str_may" />",
"<xsl:value-of select="@str_june" />",
"<xsl:value-of select="@str_july" />",
"<xsl:value-of select="@str_august" />",
"<xsl:value-of select="@str_september" />",
"<xsl:value-of select="@str_october" />",
"<xsl:value-of select="@str_november" />",
"<xsl:value-of select="@str_december" />");

Calendar._TT = {};
Calendar._TT["TOGGLE"] = "<xsl:value-of select="@str_changes_first_wee_day" />";
Calendar._TT["PREV_YEAR"] = "<xsl:value-of select="@str_previous_year" />";
Calendar._TT["PREV_MONTH"] = "<xsl:value-of select="@str_previous_month" />";
Calendar._TT["GO_TODAY"] = "<xsl:value-of select="@str_go_today" />";
Calendar._TT["NEXT_MONTH"] = "<xsl:value-of select="@str_next_month" />";
Calendar._TT["NEXT_YEAR"] = "<xsl:value-of select="@str_next_year" />";
Calendar._TT["SEL_DATE"] = "<xsl:value-of select="@str_select_date" />";
Calendar._TT["DRAG_TO_MOVE"] = "<xsl:value-of select="@str_drag_to_move" />";
Calendar._TT["PART_TODAY"] = "<xsl:value-of select="@str_today_with_brackets" />";
Calendar._TT["MON_FIRST"] = "<xsl:value-of select="@str_show_monday_first" />";
Calendar._TT["SUN_FIRST"] = "<xsl:value-of select="@str_show_sunday_first" />";
Calendar._TT["CLOSE"] = "<xsl:value-of select="@str_close" />";
Calendar._TT["TODAY"] = "<xsl:value-of select="@str_today" />";

Calendar._TT["WK"] = "<xsl:value-of select="@str_week_short" />";

Calendar._TT["DEF_DATE_FORMAT"] = "<xsl:value-of select="@fmt_default_date_format" />";
Calendar._TT["TT_DATE_FORMAT"] = "D, M d";

</xsl:template>	

</xsl:stylesheet> 