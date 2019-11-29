var App = function () {
    // iCheck
    var _masterCheckbox;
    var _checkbox;
    // 用于存放 ID 的数组
    var _idArray;
    /**
     * 私有方法，初始化 ICheck
     */
    var handlerInitCheckbox = function () {
        // 激活
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue'
        });
        // 获取控制端 Checkbox
        _masterCheckbox = $('input[type="checkbox"].minimal.icheck_master');
        // 获取全部 Checkbox 集合
        _checkbox = $('input[type="checkbox"].minimal');
    };
    /**
     * Checkbox 全选功能
     */
    var handlerCheckboxAll = function () {
        _masterCheckbox.on("ifClicked", function (e) {
            // 返回 true 表示未选中
            if (e.target.checked) {
                _checkbox.iCheck("uncheck");
            }
            // 选中状态
            else {
                _checkbox.iCheck("check");
            }
        });
    };
    /**
     * 删除单条记录
     * @param url 删除链接
     * @param id 需要删除数据的 ID
     */
    var handlerDeleteSingle = function (url, id) {
        // 将 ID 放入数组中，以便和批量删除通用
        _idArray = new Array();
        _idArray.push(id);
        if(window.confirm('您确定删除数据项吗？')){
            handlerDeleteData(url);
        }else{
            return false;
        }
    };
    /**
     * 批量删除
     */
    var handlerDeleteMulti = function (url) {
        _idArray = new Array();
        // 将选中元素的 ID 放入数组中
        _checkbox.each(function () {
            var _id = $(this).attr("id");
            if (_id != null && _id != "undefined" && $(this).is(":checked")) {
                _idArray.push(_id);
            }
        });
        // 判断用户是否选择了数据项
        if (_idArray.length === 0) {
            alert("您还没有选择任何数据项，请至少选择一项");
        }
        else {
            if(window.confirm('确定删除当前选中数据吗？')){
                handlerDeleteData(url);
            }else{
                return false;
            }
        }
    };
    /**
     * AJAX 异步删除
     * @param url
     */
    var handlerDeleteData = function (url) {
        if (_idArray.length > 0) {
            // AJAX 异步删除操作
            setTimeout(function () {
                $.ajax({
                    "url": url,
                    "type": "POST",
                    "data": {"ids": _idArray.toString()},
                    "dataType": "JSON",
                    "success": function (data) {
                        // 请求成功
                        if (data.success.endsWith("成功")) {
                            // 刷新页面
                            alert("删除成功");
                            window.location.reload();
                        }
                        // 请求失败
                        else {
                            alert("删除失败，请重试");
                            window.location.reload();
                        }
                    }
                });
            }, 500)
        }
    };
    /**
     * 初始化 DataTables
     */
    var handlerInitDataTables = function (url, columns) {
        var _dataTable = $("#dataTable").DataTable({
            "paging": true,
            "info": true,
            "lengthChange": false,
            "ordering": false,
            "processing": true,
            "searching": false,
            "serverSide": true,
            "deferRender": true,
            "ajax": {
                "url": url
            },
            "columns": columns,
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            "drawCallback": function (settings) {
                handlerInitCheckbox();
                handlerCheckboxAll();
            }
        });
        return _dataTable;
    };

    return {
        /**
         * 初始化
         */
        init: function () {
            handlerInitCheckbox();
            handlerCheckboxAll();
        },
        /**
         * 删除单条数据
         * @param url
         */
        deleteSingle: function (url, id, msg) {
            handlerDeleteSingle(url, id, msg);
        },
        /**
         * 批量删除
         * @param url
         */
        deleteMulti: function (url) {
            handlerDeleteMulti(url);
        },
        /**
         * 初始化 DataTables
         * @param url
         * @param columns
         * @returns {*|jQuery}
         */
        initDataTables: function (url, columns) {
            return handlerInitDataTables(url, columns);
        }
    }
}();

$(document).ready(function () {
    App.init();
});