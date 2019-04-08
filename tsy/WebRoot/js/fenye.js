// JavaScript Document By lishewen     
var theTable = document.getElementById("tablelsw");
var totalPage = document.getElementById("spanTotalPage");
var pageNum = document.getElementById("spanPageNum");
var spanPre = document.getElementById("spanPre");
var spanNext = document.getElementById("spanNext");
var spanFirst = document.getElementById("spanFirst");
var spanLast = document.getElementById("spanLast");
var alltiao = document.getElementById("alltiao");

var numberRowsInTable = theTable.rows.length;
var pageSize = 10;
var page = 1;
// 下一页
function next() {
	hideTable();
	currentRow = pageSize * page;
	maxRow = currentRow + pageSize;
	if (maxRow > numberRowsInTable)
		maxRow = numberRowsInTable;
	for ( var i = currentRow; i < maxRow; i++) {
		theTable.rows[i].style.display = '';
	}
	page++;
	if (maxRow == numberRowsInTable) {
		nextText();
		lastText();
	}
	showPage();
	preLink();
	firstLink();
}
// 上一页
function pre() {
	hideTable();
	page--;
	currentRow = pageSize * page;
	maxRow = currentRow - pageSize;
	if (currentRow > numberRowsInTable)
		currentRow = numberRowsInTable;
	for ( var i = maxRow; i < currentRow; i++) {
		theTable.rows[i].style.display = '';
	}
	if (maxRow == 0) {
		preText();
		firstText();
	}
	showPage();
	nextLink();
	lastLink();
}
// 第一页
function first() {
	hideTable();
	page = 1;
	for ( var i = 0; i < pageSize; i++) {
		theTable.rows[i].style.display = '';
	}
	showPage();
	preText();
	firstText();
	nextLink();
	lastLink();
}
// 最后一页
function last() {
	hideTable();
	page = pageCount();
	currentRow = pageSize * (page - 1);
	for ( var i = currentRow; i < numberRowsInTable; i++) {
		theTable.rows[i].style.display = '';
	}
	showPage();
	preLink();
	firstLink();
	nextText();
	lastText();
}

function hideTable() {
	for ( var i = 0; i < numberRowsInTable; i++) {
		theTable.rows[i].style.display = 'none';
	}
}

function showPage() {
	pageNum.innerHTML = page;
}
// 总共页数
function pageCount() {
	var count = 0;
	if (numberRowsInTable % pageSize != 0)
		count = 1;
	return parseInt(numberRowsInTable / pageSize) + count;
}
// 显示链接
function preLink() {
	spanPre.innerHTML = "<a href='javascript:pre();'>上一页</a>";
}

function preText() {
	spanPre.innerHTML = "<a style='color:#8B8386;'>上一页</a>";
}

function nextLink() {
	if (numberRowsInTable <= 10) {
		spanNext.innerHTML = "<a style='color:#8B8386;'>下一页</a>";
	} else {
		spanNext.innerHTML = "<a href='javascript:next();'>下一页</a>";
	}
}

function nextText() {
	spanNext.innerHTML = "<a style='color:#8B8386;'>下一页</a>";
}

function firstLink() {
	spanFirst.innerHTML = "<a href='javascript:first();'>第一页</a>";
}

function firstText() {
	spanFirst.innerHTML = "<a style='color:#8B8386;'>第一页</a>";
}

function lastLink() {
	if (numberRowsInTable <= 10) {
		spanLast.innerHTML = "<a style='color:#8B8386;'>最后一页</a>";
	} else {
		spanLast.innerHTML = "<a href='javascript:last();'>最后一页</a>";
	}
}

function lastText() {
	spanLast.innerHTML = "<a style='color:#8B8386;'>最后一页</a>";
}
// 隐藏表格
function hide() {
	numberRowsInTable = theTable.rows.length;
	for ( var i = pageSize; i < numberRowsInTable; i++) {
		theTable.rows[i].style.display = 'none';
	}
	alltiao.innerHTML = numberRowsInTable;
	totalPage.innerHTML = pageCount();
	pageNum.innerHTML = '1';
	nextLink();
	lastLink();
}
hide();