app.controller('itemCatController', function ($scope, baseService, $controller) {
    /* 指定继承baseController*/
    $controller('baseController', {$scope: $scope});

    /** 根据parentId查询所有的商品分类 */
    $scope.findByParentId = function (parentId) {
        baseService.sendGet("/itemCat/findAllByParentId", "parentId=" + parentId)
            .then(function (response) {
                if (response.data.length > 0) {
                    $scope.dataList = response.data;
                } else {
                    $scope.navigationItems.pop();
                    alert("没有下级分类了");
                }
            });
    };


    /** 导航条面包屑*/
    $scope.navigationItems = [];
    // 添加内容
    $scope.addNavItem = function (itemCat) {
        $scope.navigationItems.push(itemCat);
    };
    // 删除内容
    $scope.removeNavItem = function (index) {
        var num = $scope.navigationItems.length - index;
        $scope.navigationItems.splice(index,num);
    }


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
            $scope.entity = {};
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