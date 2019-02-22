app.controller('specificationController', function ($scope, $controller, baseService) {
    //指定继承baseController
    $controller('baseController', {$scope: $scope});

    $scope.findByPage = function (pageNum, pageSize) {
        baseService.findByPage("/specification/findByPage", pageNum, pageSize, $scope.search)
            .then(function (response) {
                $scope.dataList = response.data.list;
                $scope.paginationConf.totalItems = response.data.totalNum;
            });
    };

    /** 新增规格选项行 */
    $scope.addTableRow = function () {
        $scope.entity.specificationOptions.push({});
    };

    /** 新增规格选项行 */
    $scope.deleteTableRow = function (index) {
        $scope.entity.specificationOptions.splice(index, 1);
    };

    /** 更新或者添加规格*/
    $scope.saveOrUpdate = function () {
        var URL = "insert";
        if ($scope.entity.id) {
            URL = "update";
        }
        baseService.sendPost("/specification/" + URL, $scope.entity).then(
            function (response) {
                $scope.reload();
            },function (response) {
                alert('操作失败')
            }
        )
    };

    /** 回显规格数据*/
    $scope.show = function (spec) {
        $scope.entity = {};
        $scope.entity.id = spec.id;
        $scope.entity.specName = spec.specName;
        baseService.sendGet("/specificationOption/findOptionsBySpecId","id="+spec.id)
            .then(function (response) {
                $scope.entity.specificationOptions = response.data;
            });
    }

    /** 删除品牌*/
    $scope.delete = function () {
        if($scope.ids.length > 0){
            if(confirm("您确定要删除吗")){
                baseService.deleteById("/specification/delete",$scope.ids)
                    .then(function (response) {
                        $scope.ids = [];
                        $scope.reload();
                    },function (response) {
                        alert("删除失败");
                    })
            }
        } else {
           alert("请选择要删除的品牌");
        }
    }
});
