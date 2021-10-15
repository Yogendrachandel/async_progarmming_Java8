package com.learn.completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.learn.domain.Inventory;
import com.learn.domain.Product;
import com.learn.domain.ProductInfo;
import com.learn.domain.ProductOption;
import com.learn.domain.Review;
import com.learn.service.InventoryService;
import com.learn.service.ProductInfoService;
import com.learn.service.ReviewService;
import com.learn.util.LoggerUtil;
import static com.learn.util.CommonUtil.stopWatch;

public class ProductServiceUsingCompletablaFuture {
	
	 private ProductInfoService productInfoService;
	 private ReviewService reviewService;
     private InventoryService inventoryService;
	
	public ProductServiceUsingCompletablaFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }
	
	 public ProductServiceUsingCompletablaFuture(ProductInfoService productInfoService, ReviewService reviewService, InventoryService inventoryervice) {
	        this.productInfoService = productInfoService;
	        this.reviewService = reviewService;
	        this.inventoryService = inventoryervice;
	    }

	// CASE1: assume this method is works as client so we join method to block the	thread.
  /*
	public Product retrieveProductDetails(String productId) {
	  
	  CompletableFuture<ProductInfo> productInfo = CompletableFuture
	  .supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
	  
	  CompletableFuture<Review> cfReview = CompletableFuture .supplyAsync(() ->
	  reviewService.retrieveReviews(productId));
	  
	  // Join both the result to get final Product .
	  
	  Product product = productInfo.thenCombine(cfReview, (productInformation,
	  review) -> new Product(productId, productInformation, review)) .join();//It is blocking this line only. 
	  return product;
	  }
	*/  
	 
	/*CASE :2,Assume this method act as server so there is no need to blocking anything,client can be responsible to perform
	blocking at there side*/
	public CompletableFuture<Product> retrieveProductDetails(String productId) {

		CompletableFuture<ProductInfo> productInfo = CompletableFuture
				.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));

		CompletableFuture<Review> cfReview = CompletableFuture
				.supplyAsync(() -> reviewService.retrieveReviews(productId));

		// Join both the result to get final Product .

		CompletableFuture<Product> product = productInfo.thenCombine(cfReview,
				(productInformation, review) -> new Product(productId, productInformation, review));
		return product;
	}

	
	public Product retrieveProductDetailsWithInventory(String productId) {
		 
		 //First we get the ProductInfo object using Asysn call  of Completable future
		CompletableFuture<ProductInfo> cfProductInfo=CompletableFuture.supplyAsync(()->
		productInfoService.retrieveProductInfo(productId)).
		thenApply((productInfo)->{productInfo.setProductOptions(updateInventory_Approach2(productInfo));
			return productInfo;
		});	
		
		CompletableFuture<Review> cfReview = CompletableFuture
                .supplyAsync(()-> reviewService.retrieveReviews(productId));
		
		Product product = cfProductInfo.thenCombine(cfReview, (productInfo,review)->new Product(productId, productInfo, review)).join();
		
		return product;
    }
	
	//Update the Inventrory of each Productoption object and return list ProductOption 
	public List<ProductOption> updateInventory_Approach1(ProductInfo productInfo){
		 stopWatch.start();
		 List<ProductOption> productOptionList=productInfo.getProductOptions().stream()
				.map(eachProductoption->{
					 Inventory inventory = inventoryService.retrieveInventory(eachProductoption);
					eachProductoption.setInventory(inventory);
					return eachProductoption;
				})
				.collect(Collectors.toList());
		 stopWatch.stop();
	     LoggerUtil.log("Total Time Taken by updateInventory_Approach1(): "+ stopWatch.getTime());
		 return productOptionList;
		
	}
	
	
	    //Update the Inventrory of each Productoption object and return list ProductOption.This approach takes less time than Approach1 -updateInventory_Approach1()
		public List<ProductOption> updateInventory_Approach2(ProductInfo productInfo) {
			stopWatch.start();
			List<CompletableFuture<ProductOption>> productOptionList = productInfo.getProductOptions().stream().
			map(eachProductoption-> {
					return	CompletableFuture.supplyAsync(() -> inventoryService.retrieveInventory(eachProductoption))
								.thenApply(inventoryResult -> {
									eachProductoption.setInventory(inventoryResult);
									System.out.println("fg");
								
									return eachProductoption;
								});
					}).collect(Collectors.toList());
			 stopWatch.stop();
		     LoggerUtil.log("Total Time Taken by updateInventory_Approach2(): "+ stopWatch.getTime());
			
		     
		     //This statement collect the result set in the list  of above mention Async call runs parellely  
		     return productOptionList.stream().map(CompletableFuture::join).collect(Collectors.toList());

		}

	
	
	
	
	
	
	public static void main(String[] args) {

		    ProductInfoService productInfoService = new ProductInfoService();
	        ReviewService reviewService = new ReviewService();
	        InventoryService inventoryService=new InventoryService();
	        ProductServiceUsingCompletablaFuture productService = new ProductServiceUsingCompletablaFuture(productInfoService, reviewService);
	        ProductServiceUsingCompletablaFuture productServiceCF=new ProductServiceUsingCompletablaFuture(productInfoService, reviewService,inventoryService);
	        String productId = "ABC123";
	        CompletableFuture<Product> product = productService.retrieveProductDetails(productId);
	        Product productWithInventory=productServiceCF.retrieveProductDetailsWithInventory(productId);

	        try {
				LoggerUtil.log("Product without Inventory " + product.get());
				LoggerUtil.log("Product with Inventory " + productWithInventory);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
