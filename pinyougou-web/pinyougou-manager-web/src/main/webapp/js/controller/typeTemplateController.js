app.controller('typeTemplateController', function ($scope, baseService, $controller) {
    /* 指定继承baseController*/
    $controller('baseController', {$scope: $scope});

    /*分页查询*/
    $scope.searchEntity = {};
    $scope.findByPage = function (pageNum, pageSize) {
        baseService.findByPage("/typeTemplate/findByPage", pageNum,
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

    /** 定义brandList和findAllBrands方法*/
    $scope.brandList = {}
    $scope.findAllBrands = function () {
        baseService.sendGet("/brand/findBrandList").then(
            function (response) {
                $scope.brandList.data = response.data;
            }
        )
    };

    /** 定义specificationList和findAllSpecification方法*/
    $scope.specificationList = {}
    $scope.findAllSpecifications = function () {
        baseService.sendGet("/specification/findSpecificationList").then(
            function (response) {
                $scope.specificationList.data = response.data;
            }
        )
    };

    /** 新增扩展属性行 */
    $scope.addTableRow = function () {
        $scope.entity.customAttributeItems.push({});
    };

    /** 删除扩展属性行 */
    $scope.deleteTableRow = function (index) {
        $scope.entity.customAttributeItems.splice(index, 1);
    };


    /*数据绑定*/
    $scope.show = function (typeTemplate) {
        $scope.entity = JSON.parse(JSON.stringify(typeTemplate));
        /** 转换品牌列表 */
        $scope.entity.brandIds = JSON.parse(typeTemplate.brandIds);
        /** 转换规格列表 */
        $scope.entity.specIds = JSON.parse(typeTemplate.specIds);
        /** 转换扩展属性 */
        $scope.entity.customAttributeItems = JSON.parse(typeTemplate.customAttributeItems);
    };


    /* 添加或者更新品牌*/
    $scope.saveOrUpdate = function () {
        var URL = "insert";
        if ($scope.entity.id) {
            URL = "update";
        }
        baseService.sendPost("/typeTemplate/" + URL, $scope.entity).then(function (response) {
            //清空编辑框的数据
            $scope.entity={};
            $scope.reload();
        }, function (response) {
            alert("保存失败");
        })
    };

    /**删除品牌*/
    $scope.delete = function () {
        if ($scope.ids.length > 0) {
            if (confirm("你真的确定要删除吗？")) {
                baseService.deleteById("/typeTemplate/delete", $scope.ids).then(function (response) {
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