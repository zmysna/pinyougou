app.controller('baseController',function ($scope) {
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
        alert($scope.ids)
    };
})