app.controller('baseController', function ($scope) {

    /* 设置分页参数 */
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 0,
        itemsPerPage: 10,
        perPageOptions: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        onChange: function () {
            $scope.reload();
        }
    };

    /** 重新加载数据 */
    $scope.reload = function () {
        $scope.findByPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    /* 将被勾选选项的id添加到ids*/
    $scope.ids = [];
    $scope.updateSelection = function ($event, id) {
        if ($event.target.checked) {
            $scope.ids.push(id);
        } else {
            // 取消勾选则从ids中删除选项id
            var idx = $scope.ids.indexOf(id);
            $scope.ids.splice(idx, 1);
        }
        alert($scope.ids);
    };

    /** 将JSON数组字符串转换成可读性更强的字符串（用逗号分隔）
     *  @Param jsonStr json字符串
     *  @Param field 要转换的域的名称
     */
        $scope.jsonArrStrToStr = function (jsonArrStr, field) {
        // JSON数组字符串转换成json对象
        var jsonArr = JSON.parse(jsonArrStr);
        // 定义一个新数组
        var resArr = [];
        for (var i = 0; i < jsonArr.length; i++) {
            var json = jsonArr[i];
            resArr.push(json[field]);
        }
        return resArr.join(",");
    }
});