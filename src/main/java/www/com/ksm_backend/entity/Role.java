package www.com.ksm_backend.entity;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    CEO,
    DEV,
    ADMIN

//    @Getter
//    private final Set<Permission> permissions;
//
//    public List<SimpleGrantedAuthority> getAuthorities() {
//        var authorities = getPermissions()
//                .stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toList());
//        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
//        return authorities;
//    }
}