app.controller("ngBrandController", function ($scope, $controller, baseService) {

    /** 指定继承baseController */
    $controller('baseController',{$scope:$scope});

    /*分页查询*/
    $scope.searchEntity = {};
    $scope.findByPage = function (pageNum, pageSize) {
        baseService.findByPage("/brand/findByPage", pageNum,
            pageSize, $scope.searchEntity)
            .then(
            function (response) {
                $scope.dataList = response.data.list;
                $scope.paginationConf.totalItems = response.data.totalNum;
            }, function (response) {
                alert("服务器正在忙");
            }
        )

    };

    /*数据绑定*/
    $scope.show = function (brand) {
        var jsonStr = JSON.stringify(brand);
        $scope.entity = JSON.parse(jsonStr);
    };

    /* 添加或者更新品牌*/
    $scope.saveOrUpdate = function () {
        var URL = "insert";
        if ($scope.entity.id) {
            URL = "update";
        }
        baseService.sendPost("/brand/" + url, $scope.entity).then(function (response) {
            $scope.reload();
        }, function (response) {
            alert("保存失败");
        })
    };

    /**删除品牌*/
    $scope.delete = function () {
        if ($scope.ids.length > 0) {
            if (confirm("你真的确定要删除吗？")) {
                baseService.deleteById("/brand/delete", $scope.ids).then(function (response) {
                    $scope.ids = [];
                    $scope.reload();
                }, function (response) {
                    alert("删除失败");
                })
            }
        } else {
            alert("请选择要删除的品牌")
        }
    };

});
