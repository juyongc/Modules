package com.kakaooauth2service;

import jakarta.servlet.http.HttpFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirebaseTokenFilter extends HttpFilter {

//    /**
//     * [해당 필터 역할]
//     * 파이어베이스를 통해 idToken 유효성 확인 후 인증 정보 등록 및 확인
//     * 유효성 확인되면 SecurityContextHolder로 SecurityContext 생성
//     * authentication 객체 생성 후 SecurityContext에 setAuthentication
//     * SecurityContextHolder에 SecurityContext setContext
//     */
//    @Override
//    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//        throws IOException, ServletException {
//        String token = getBearerToken(request);
//
//        if (token != null) {
//            try {
//
//                // 파이어베이스로 토큰 유효성 검사 확인
//                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
//                String uid = decodedToken.getUid();
//
//                // 커스텀 토큰 만들어서 프론트와 통신
//                // 로그인해서 이미 커스텀 정보가 추가된 상태면 생략
//                if (!decodedToken.getClaims().containsKey("addToken")) {
//                    String customToken = addCustomToken(uid);
//                    log.info("add token = " + customToken);
//                    response.setHeader("customToken", customToken);
//                }
//
////                 실제로는 유저별 권한 가져와서 등록해야함
////                String authority = memberRepository.findById(uid).getAuthority();
//                String authority = "ADMIN";
//
//                SecurityContext context = SecurityContextHolder.createEmptyContext();
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    uid, null, Collections.singletonList(new SimpleGrantedAuthority(authority)));
//
//                context.setAuthentication(authentication);
//                SecurityContextHolder.setContext(context);
//
//            } catch (FirebaseAuthException e) {
//                log.info(e.getMessage());
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Firebase token");
//                return;
//            }
//        }
//        chain.doFilter(request, response);
//    }
//
//    /**
//     * 커스텀 토큰 생성 기능
//     */
//    private String addCustomToken(String uid) throws FirebaseAuthException {
//
//        Map<String, Object> additionalClaims = new HashMap<>();
//
//        additionalClaims.put("addToken", "add something");
//        additionalClaims.put("authorization", "ADMIN");
//        return FirebaseAuth.getInstance().createCustomToken(uid, additionalClaims);
//    }
//
//    private String getBearerToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        super.init(filterConfig);
//    }
//
//    @Override
//    public void destroy() {
//        super.destroy();
//    }

}
