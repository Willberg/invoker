// 4. 用C语言实现malloc和free函数 https://en.wikipedia.org/wiki/C_dynamic_memory_allocation
#include <stdio.h>
#include <unistd.h>

#define NALLOC 1024

typedef long Align;

union header {
    struct {
        union header *ptr;
        unsigned size;
    } s;
    Align x;
};

typedef union header Header;

static Header base;
static Header *freep = NULL;
static Header* _morecore(unsigned);
void _free(void*);

void* _malloc(unsigned nbytes) {
    Header *p, *prev;
    unsigned nunits = (nbytes + sizeof(Header) - 1) / sizeof(Header) + 1;
    
    if ((prev=freep) == NULL) {
        base.s.ptr = freep = prev = &base;
        base.s.size = 0;
    }
    
    for(p = prev->s.ptr; ; prev = p, p = p->s.ptr) {
        if (p->s.size >= nunits) {
            if (p->s.size == nunits) {
                prev->s.ptr = p->s.ptr;
            } else {
                p->s.size -= nunits;
                p += p->s.size;
                p->s.size = nunits;
            }
            freep = prev;
            return (void*) (p+1);
        }
        if(p == freep) {
            if((p=_morecore(nunits)) == NULL) {
                return NULL;
            }
        }
    }
}

static Header* _morecore(unsigned nu) {
    if (nu < NALLOC) {
        nu = NALLOC;
    }
    
    char *cp = sbrk(nu * sizeof(Header));
    if (cp == (char *) -1) {
        return NULL;
    }
    Header *up = (Header *) cp;
    up->s.size = nu;
    _free((void *) (up + 1));
    return freep;
}

void _free(void* ap) {
    Header *bp = (Header *) ap - 1, *p;
    for (p = freep; !(p < bp && bp < p->s.ptr); p = p->s.ptr) {
        if (p >= p->s.ptr && (bp > p || bp < p->s.ptr)) {
            break;
        }
    }
    if (bp + bp->s.size == p->s.ptr) {
        bp->s.size += p->s.ptr->s.size;
        bp->s.ptr = p->s.ptr->s.ptr;
    } else {
        bp->s.ptr = p->s.ptr;
    }
    if(p + p->s.size == bp) {
        p->s.size += bp->s.size;
        p->s.ptr = bp->s.ptr;
    } else {
        p->s.ptr = bp;
    }
    freep = p;
}

void test_mymalloc(int n) {
    int *p = (int*) _malloc(n*sizeof(int));
    if (p == NULL) {
        return;
    }
    for(int i=0; i<n; i++) {
        p[i] = i+1;
    }
    for (int i=0; i<n; i++) {
        printf("%d,", p[i]);
    }
    _free(p);
}
