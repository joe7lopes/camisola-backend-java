package com.camisola10.camisolabackend.rest.product;

import com.camisola10.camisolabackend.rest.ApiUrl;
import com.camisola10.camisolabackend.rest.ControllerTest;
import com.camisola10.camisolabackend.application.port.in.ProductsCommandService;
import com.camisola10.camisolabackend.application.port.in.ProductsCommandService.UpdateProductCommand;
import com.camisola10.camisolabackend.application.port.in.ProductsQueryService;
import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.application.port.in.command.product.RemoveProductCommand;
import com.camisola10.camisolabackend.domain.product.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(controllers = ProductController.class)
@ActiveProfiles("local")
class ProductControllerTest {

    @MockBean
    private ProductsCommandService productsCommandService;

    @MockBean
    private ProductsQueryService productsQueryService;

    @MockBean
    private ProductRequestMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get(ApiUrl.PRODUCTS)
                .contentType(APPLICATION_JSON))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void shouldReturnListOfProducts() throws Exception {
        var product = mock(Product.class);
        when(productsQueryService.getAll()).thenReturn(List.of(product));
        when(mapper.map(any(Product.class))).thenReturn(new ProductResponse("123", "p1", null, null, true, null, "23", ""));

        mockMvc.perform(get(ApiUrl.PRODUCTS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].name").value("p1"))
                .andExpect(jsonPath("$[0].customizable").value(true))
                .andExpect(jsonPath("$[0].defaultPrice").value("23"));

        verify(productsQueryService).getAll();
        verify(mapper).map(any(Product.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldCreateNewProduct() throws Exception {

        JSONObject requestBody = createRequestBody();

        var commandMock = mock(CreateProductCommand.class);
        var productMock = mock(Product.class);
        when(mapper.map(any(CreateProductRequest.class))).thenReturn(commandMock);
        when(productsCommandService.createProduct(commandMock)).thenReturn(productMock);
        var response = ProductResponse.builder()
                .name("camisola slb")
                .build();
        when(mapper.map(productMock)).thenReturn(response);

        mockMvc.perform(post(ApiUrl.PRODUCTS)
                .contentType(APPLICATION_JSON)
                .content(requestBody.toString()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(requestBody.get("name")));


        verify(mapper).map(any(CreateProductRequest.class));
        verify(productsCommandService).createProduct(commandMock);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldNotAllowRoleUserToCreateProducts() throws Exception {
        mockMvc.perform(post(ApiUrl.PRODUCTS)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldOnlyAllowAdminRoleToCreateProducts() throws Exception {
        var requestBody = createRequestBody();
        mockMvc.perform(post(ApiUrl.PRODUCTS)
                .contentType(APPLICATION_JSON)
                .content(requestBody.toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldNotAllowUnauthenticatedUsersToCreateProducts() throws Exception {
        mockMvc.perform(post(ApiUrl.PRODUCTS)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldNotAllowUnauthenticatedUsersToUpdateProducts() throws Exception {
        mockMvc.perform(put(ApiUrl.PRODUCTS + "/123")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldUpdateProduct() throws Exception {
        var requestBody = createRequestBody();
        var command = mock(UpdateProductCommand.class);
        var product = mock(Product.class);
        when(mapper.map(any(UpdateProductRequest.class))).thenReturn(command);
        when(productsCommandService.updateProduct(command)).thenReturn(product);

        mockMvc.perform(put(ApiUrl.PRODUCTS + "/123")
                .contentType(APPLICATION_JSON)
                .content(requestBody.toString()))
                .andExpect(status().isOk());

        verify(mapper).map(any(UpdateProductRequest.class));
        verify(productsCommandService).updateProduct(command);
        verify(mapper).map(product);
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(productsCommandService);
    }

    @Test
    public void shouldOnlyAllowAdminUserToDeleteProduct() throws Exception {
        mockMvc.perform(delete(ApiUrl.PRODUCTS + "/123")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldDeleteProductById() throws Exception {
        var productId = Product.ProductId.create();

        mockMvc.perform(delete(ApiUrl.PRODUCTS + "/" + productId.asString())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productsCommandService).removeProduct(any(RemoveProductCommand.class));
        verifyNoMoreInteractions(productsCommandService);
    }

    private JSONObject createRequestBody() throws JSONException {
        var s1 = new JSONObject();
        s1.put("size", "S");
        s1.put("price", "23");

        var s2 = new JSONObject();
        s2.put("size", "XL");
        s2.put("price", "27");
        var sizes = new JSONArray();
        sizes.put(s1);
        sizes.put(s2);

        var categories = new JSONArray();
        categories.put("benfica");
        categories.put("camisolas");

        var requestBody = new JSONObject();
        requestBody.put("name", "camisola slb");
        requestBody.put("sizes", sizes);
        requestBody.put("categories", categories);
        requestBody.put("defaultPrice", "40");
        requestBody.put("isCustomizable", true);
        return requestBody;
    }

}
