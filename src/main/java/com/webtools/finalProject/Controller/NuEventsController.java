package com.webtools.finalProject.Controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.razorpay.RazorpayException;
import com.webtools.finalProject.Dao.NuEventsDao;
import com.webtools.finalProject.Dao.UserDao;
import com.webtools.finalProject.Dao.UserProductDao;
import com.webtools.finalProject.Dao.UserOrderDao;
import com.webtools.finalProject.Dao.UserWishlistDao;
import com.webtools.finalProject.Exception.UserException;
import com.webtools.finalProject.Pojo.NuEvents;
import com.webtools.finalProject.Pojo.User;
import com.webtools.finalProject.Pojo.UserOrderMap;
import com.webtools.finalProject.Pojo.UserProductMap;
import com.webtools.finalProject.Pojo.UserWishlistMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class NuEventsController {
	
	String id = null;
	int count =0 ;
	int updateValue =0;
	int totalSelected = 0 ;
	List<NuEvents> cartItemsList = new ArrayList<NuEvents>();
	List<NuEvents> wishlistItemsList = new ArrayList<NuEvents>();
	List<NuEvents> searchedItems = new ArrayList<NuEvents>();
	List<NuEvents> sortedItems = new ArrayList<NuEvents>();
	List<NuEvents> ordersList = new ArrayList<NuEvents>();
	List<NuEvents> cartList = new ArrayList<NuEvents>();
	List<NuEvents> wishList = new ArrayList<NuEvents>();
	List<NuEvents> orderCartList = new ArrayList<NuEvents>();
	List<NuEvents> paginationResults = new ArrayList<NuEvents>();
	List<NuEvents> previousOrderList = new ArrayList<NuEvents>();

	
	int totalCost = 0;
	int aTotalCost = 0;
	int optionSelected = 0;
	NuEventsDao tdao = new NuEventsDao();

	

	
	@PostMapping("/products.htm")
	public String handleLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("nuEvents") NuEvents travelPackage, @ModelAttribute("userproduct") UserProductMap userproduct, BindingResult result) throws UserException, RazorpayException {
		
				String userSelectedOption = request.getParameter("userSelectedOption");
		System.out.println(userSelectedOption);
		
		if(userSelectedOption.contains("Add To Cart")) {
			cartList = (List<NuEvents>) session.getAttribute("travelPackagesCart");
			int cartCount =0;
//			UserDao userDao = new UserDao();
//			User currentUser = userDao.getUser(user.getName());
			String pid = userSelectedOption.substring(12);
			Integer tid= Integer.parseInt(pid);		
			
			NuEvents addTocart=tdao.getSelectedProduct(tid);
//			userproduct.setTravelPackages(addTocart);
//			userproduct.setUser(user);
			UserProductDao updao = new UserProductDao();
			User user = (User) session.getAttribute("currentUser");
			System.out.println("Logged In User for Cart "+ user);
			System.out.println("Product Added To Cart"+ addTocart);
			
			System.out.println(addTocart.getPackageName());
			System.out.println(addTocart.getPackageDescription());
			System.out.println(addTocart.getPackagePrice());
			
			if(cartList.size() > 0) {
				for(NuEvents i : cartList) {
					if(addTocart.getPackageId() == i.getPackageId()  ) {
						System.out.println("Item already exists!");
						cartCount += 1;
						request.setAttribute("cartError", "Item already exists");
						
						return "dashboard1";
					}
				}if(cartCount == 0) {
					cartList.add(addTocart);
					UserProductMap upmap = updao.create(new UserProductMap(user, addTocart));
					request.setAttribute("cartMessage", "Item added ");
					
					return "dashboard1";
				}else {
					System.out.println("Item exists");
					request.setAttribute("cartError", "Item already exists");
					
					return "dashboard1";
				}
			
		
			}else {
				cartList.add(addTocart);
				UserProductMap upmap = updao.create(new UserProductMap(user, addTocart));
				
				
				
			}
			
			session.setAttribute("travelPackagesCart", cartList);
//			request.setAttribute("cartMessage", "Item added ");
//			return "dashboard1";
		}
		else if(userSelectedOption.contains("Add To Wishlist")){
			int wishCount =0;
			wishList = (List<NuEvents>) session.getAttribute("travelPackagesWishlist");
			String pid = userSelectedOption.substring(16);
			Integer tid= Integer.parseInt(pid);
			for(NuEvents wish : wishList) {
				System.out.println(wish.getPackageId());
			}
			
			NuEvents addToWishlist=tdao.getSelectedProduct(tid);
			
			UserWishlistDao uwdao = new UserWishlistDao();
			User user = (User) session.getAttribute("currentUser");
			System.out.println("Logged In User for Wishlist:"+ user);
			System.out.println("Product added to wishlist" + addToWishlist);
			
			
			System.out.println(addToWishlist.getPackageName());
			System.out.println(addToWishlist.getPackageDescription());
			System.out.println(addToWishlist.getPackagePrice());
			//wishlistItemsList.add(addToWishlist);
			if(wishList.size() > 0) {
				for(NuEvents i : wishList) {
					if(addToWishlist.getPackageId() == i.getPackageId()  ) {
						System.out.println("Item already exists!");
						request.setAttribute("wishlistError", "Item already exists");
						wishCount += 1;
						return "dashboard1";
					}
				}if(wishCount == 0) {
					wishList.add(addToWishlist);
					UserWishlistMap uwmap = uwdao.create(new UserWishlistMap(user, addToWishlist));
					request.setAttribute("wishlistSuccess", "Item added successfully");
					return "dashboard1";

				}else {
					System.out.println("Item exists");
					request.setAttribute("wishlistError", "Item already exists");
					return "dashboard1";
				}
		
			}else {
				wishList.add(addToWishlist);
				UserWishlistMap uwmap = uwdao.create(new UserWishlistMap(user, addToWishlist));
				request.setAttribute("wishlistSuccess", "Item added successfully");

			}
			session.setAttribute("travelPackagesWishlist", wishList);

		}
		else if(userSelectedOption.contains("Delete")) {
			String pid = userSelectedOption.substring(8);
			int wCount =0;
			Integer tid= Integer.parseInt(pid);
			if(userSelectedOption.contains("DeleteW")) {
				wishList = (List<NuEvents>) session.getAttribute("travelPackagesWishlist");
				UserWishlistDao uwdao = new UserWishlistDao();
				
			
				
				System.out.println("Delete Id: "+tid);
				NuEvents removeItem = tdao.getSelectedProduct(tid);
				for(NuEvents i : wishList) {
					if(removeItem.getPackageId() == i.getPackageId()) {
						wishList.remove(wCount);
						uwdao.deleteSelectedWishlistItem(i);
						break;
					}	
					else {
						wCount+=1;
					}
				}

				System.out.println(wishList);			
				session.setAttribute("travelPackagesWishlist", wishList);

			}else {
				int cartCount =0;
				cartList = (List<NuEvents>) session.getAttribute("travelPackagesCart");
				UserProductDao uwdao = new UserProductDao();
				System.out.println("Delete Id: "+tid);
				NuEvents removeItem = tdao.getSelectedProduct(tid);
				for(NuEvents i : cartList) {
					if(removeItem.getPackageId() == i.getPackageId()) {
						cartList.remove(cartCount);
						uwdao.deleteSelectedCartlistItem(i);
						break;
					}	
					else {
						cartCount+=1;
					}
				}

				System.out.println(cartList);			
				session.setAttribute("travelPackagesCart", cartList);
			}
			}
		else if(userSelectedOption.contains("Orders")) {
			previousOrderList = (List<NuEvents>) session.getAttribute("travelPackagesOrders");
			orderCartList = (List<NuEvents>) session.getAttribute("travelPackagesCart");
			User user = (User) session.getAttribute("currentUser");
			UserOrderDao uodao = new UserOrderDao();
			for(NuEvents order: orderCartList) {
				ordersList.add(order);
				//aTotalCost+=order.getPackagePrice();
				uodao.create(new UserOrderMap(user, tdao.getSelectedProduct(order.getPackageId()),tdao.getSelectedProduct(order.getPackageId()).getPackagePrice()));
			}
			for(NuEvents order: previousOrderList) {
				ordersList.add(order);
			}
			cartList.clear();
			UserProductDao updao= new UserProductDao();
			updao.deleteAllTravelPackages();
			session.setAttribute("travelPackagesCart", cartList);
			System.out.println(ordersList);
			System.out.println("Orders");
			session.setAttribute("travelPackagesOrders", ordersList);
			//System.out.println(aTotalCost);					
		}
//		else if(userSelectedOption.contains("View")) {
//			String pid = userSelectedOption.substring(5);
//			Integer tid= Integer.parseInt(pid);
//			System.out.println(tid);
//			
//			TravelPackages viewItem = tdao.getSelectedProduct(tid);
//			session.setAttribute("viewItem", viewItem);
//			return new ModelAndView("view");
//	}  
		else if (userSelectedOption.contains("Search")) {
			String enteredText = request.getParameter("textEntered");
			System.out.println(enteredText);
			searchedItems=tdao.getSearchedProducts(enteredText);
			for(NuEvents i : searchedItems) {
				System.out.println(i.getPackageId());
			}
			optionSelected=1;
		}else if (userSelectedOption.contains("Sort")) {
			sortedItems=tdao.getSortedProducts();
			optionSelected=2;
		}
		else if(userSelectedOption.contains("Total")){
			totalSelected =1 ;
			orderCartList = (List<NuEvents>) session.getAttribute("travelPackagesCart");
			for(NuEvents order: orderCartList) {
				ordersList.add(order);
				aTotalCost+=order.getPackagePrice();
			}
			System.out.println(aTotalCost);					
			
//				totalCost = 0;
//				String[] selectedValues = request.getParameterValues("qty");
//				System.out.println(selectedValues);
//				
//				for (int i = 0; i < cartItemsList.size(); i++) {
//				TravelPackages item = cartItemsList.get(i);
//				totalCost += item.getPackagePrice() * Integer.parseInt(selectedValues[i]);
//				}
//				System.out.println(totalCost);
			}

		else if(userSelectedOption.matches(".*\\d+.*")){
			System.out.println("Hii");
			Integer pageNumber = Integer.parseInt(userSelectedOption);
			paginationResults = tdao.getPaginationResults(pageNumber);
			optionSelected=3;
//			System.out.println(userSelectedOption);
//			String pid = userSelectedOption.substring(5);
//			Integer tid= Integer.parseInt(pid);
//			System.out.println("tid: "+tid);
//			
//			TravelPackages viewItem = tdao.getSelectedProduct(tid);
//			session.setAttribute("viewItem", viewItem);	
//			return new ModelAndView("view");
		}
//		else if(userSelectedOption.contains("View") && userSelectedOption.matches(".*\\d+.*")) {
//			System.out.println(userSelectedOption);
//				String pid = userSelectedOption.substring(5);
//				Integer tid= Integer.parseInt(pid);
//				System.out.println("tid: "+tid);
//				
//				TravelPackages viewItem = tdao.getSelectedProduct(tid);
//				session.setAttribute("viewItem", viewItem);	
//				return "view";
//		}
		else if(userSelectedOption.contains("Update")) {
			UserDao userdao = new UserDao();
			User user = (User) session.getAttribute("currentUser");
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			userdao.updateUser(user.getId(),username, email );
			updateValue =1;
		}
		session.setAttribute("sortedItems", sortedItems);
		session.setAttribute("optionSelected", optionSelected);
		session.setAttribute("searchedItems", searchedItems);
		session.setAttribute("aTotalCost", aTotalCost);
		session.setAttribute("paginationResults", paginationResults);
		session.setAttribute("updateValue", updateValue);
		session.setAttribute("totalSelected", totalSelected);
				System.out.println();
		for(NuEvents i : wishlistItemsList) {
			System.out.println(i.getPackageId());
		}
		return "dashboard1";
		
	}

	@GetMapping("/view.htm")
	public ModelAndView handleView(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("nuEvents") NuEvents travelPackage, @ModelAttribute("userproduct") UserProductMap userproduct, BindingResult result) throws UserException{
		String userSelectedOption = request.getParameter("userSelectedOption");
		System.out.println(userSelectedOption);
		if(userSelectedOption.contains("View")) {
			String pid = userSelectedOption.substring(5);
			Integer tid= Integer.parseInt(pid);
			System.out.println("tid: "+tid);
			
			NuEvents viewItem = tdao.getSelectedProduct(tid);
			session.setAttribute("viewItem", viewItem);	
		
	}
		return new ModelAndView("view");
	
	
	}
	
	@GetMapping("/back.htm")
	public ModelAndView handleBack(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("nuEvents") NuEvents travelPackage, @ModelAttribute("userproduct") UserProductMap userproduct, BindingResult result) throws UserException{
		return new ModelAndView("dashboard1");
	
	}
	}	

